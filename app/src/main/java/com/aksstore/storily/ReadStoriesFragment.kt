package com.aksstore.storily

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aksstore.storily.databinding.FragmentStoriesBinding
import com.aksstore.storily.model.Story
import com.aksstore.storily.utils.isNightMode
import com.aksstore.storily.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


@AndroidEntryPoint
class ReadStoriesFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentStoriesBinding
    private var currentStory: Story? = null
    private lateinit var textToSpeech: TextToSpeech

    private var isPaused: Boolean = false
    private var currentUtteranceId: String? = null
    private var currentPosition: Int = 0
    private var story = ""

    private var lastStart: Int = 0
    private var lastEnd: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoriesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        populateStory()
        setupTextToSpeech()
    }

    private fun setupTextToSpeech() {
        textToSpeech = TextToSpeech(requireContext(), this)
        binding.btnSpeak.setOnClickListener {
            binding.btnPause.visibility = View.VISIBLE
            binding.btnSpeak.visibility = View.GONE
            val text = story
            if (text.isNotEmpty()) {
                speakOut(text)
            } else {
                Toast.makeText(requireContext(), "No text to read", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun populateStory() {
        if (currentStory != null) {
            binding.tvStory.text = story
            binding.ivStoryImage.loadImage(
                currentStory?.story_image,
                R.drawable.no_image_found_placeholder
            )
        }
    }

    private fun init() {
        currentStory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("story", Story::class.java)
        } else {
            arguments?.getSerializable("story") as? Story
        }
        story = currentStory?.story_description ?: ""
        currentPosition = 0
        setUpToolBar()
        animateViews()

        if (isNightMode(requireContext())) {
            binding.tvStory.setTextColor(resources.getColor(R.color.white, null))
        } else {
            binding.tvStory.setTextColor(resources.getColor(R.color.black, null))
        }

        binding.fab.bringToFront()
        binding.fab.setOnClickListener {
            if (binding.seekBar.visibility == View.GONE) {
                binding.seekBar.visibility = View.VISIBLE
            } else {
                binding.seekBar.visibility = View.GONE
            }
        }

        binding.llStoryView.setOnClickListener {
            if (binding.seekBar.visibility == View.VISIBLE) {
                binding.seekBar.visibility = View.GONE
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Update TextView font size
                binding.tvStory.textSize = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        binding.btnPlay.setOnClickListener {
            if (isPaused) {
                resumeReading()
                binding.btnSpeak.visibility = View.GONE
                binding.btnPlay.visibility = View.GONE
                binding.btnPause.visibility = View.VISIBLE

            } else {
                startReading()
                binding.btnSpeak.visibility = View.GONE
                binding.btnPlay.visibility = View.VISIBLE
                binding.btnPause.visibility = View.GONE
            }
        }

        binding.btnPause.setOnClickListener {
            pauseReading()
            binding.btnPlay.visibility = View.VISIBLE
            binding.btnPause.visibility = View.GONE
        }

    }


    private fun animateViews() {
        // Animate the ImageView
        val imageViewAnimator = ObjectAnimator.ofFloat(binding.ivStoryImage, "alpha", 0f, 1f)
        imageViewAnimator.duration = 1000
        imageViewAnimator.interpolator = AccelerateDecelerateInterpolator()
        imageViewAnimator.start()

        // Animate the TextView
        val textViewAnimator = ObjectAnimator.ofFloat(binding.tvStory, "translationY", 200f, 0f)
        textViewAnimator.duration = 1000
        textViewAnimator.interpolator = AccelerateDecelerateInterpolator()
        textViewAnimator.start()
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setUpToolBar() {
        if (!currentStory?.story_title.isNullOrEmpty()) {
            requireActivity().title = currentStory?.story_title
        } else {
            requireActivity().title = resources.getString(R.string.story)
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(requireContext(), "Language not supported", Toast.LENGTH_SHORT)
                    .show()
            }

            textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String) {
                    currentUtteranceId = utteranceId
                    isPaused = false
                }

                override fun onDone(utteranceId: String) {
                    currentUtteranceId = null

                }

                override fun onError(utteranceId: String) {
                }

                override fun onRangeStart(
                    utteranceId: String,
                    start: Int,
                    end: Int,
                    frame: Int
                ) {
                    // Use a CoroutineScope to handle the threading
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            highlightText(lastStart, lastEnd)
                            scrollToPosition(start)
                        } catch (e : Exception) {
                            Log.d("TAG", "onRangeStart: $e")
                        }

                    }
                    lastStart = start
                    lastEnd = end
                }
            })
        } else {
            Toast.makeText(requireContext(), "Initialization failed", Toast.LENGTH_SHORT).show()
        }


    }

    private fun scrollToPosition(position: Int) {
        val layout = binding.tvStory.layout
        val line = layout.getLineForOffset(position)
        val y = layout.getLineTop(line)
        binding.svStory.smoothScrollTo(0, y)
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    private fun speakOut(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    private fun highlightText(start: Int, end: Int) {
        val textWithHighlights: Spannable = SpannableString(story)
        textWithHighlights.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        binding.tvStory.text = textWithHighlights
    }


    private fun startReading() {
        val params = Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId")
        textToSpeech.speak(story, TextToSpeech.QUEUE_FLUSH, params, "utteranceId")
    }

    private fun pauseReading() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
            isPaused = true
        }
    }

    private fun resumeReading() {
        if (currentPosition < story.length) {
            val remainingText = story.substring(currentPosition)
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId")
            textToSpeech.speak(remainingText, TextToSpeech.QUEUE_FLUSH, params, "utteranceId")
            isPaused = false
        }
    }

}