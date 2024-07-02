package com.aksstore.storily

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aksstore.storily.databinding.FragmentStoriesBinding
import com.aksstore.storily.model.Story
import com.aksstore.storily.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ReadStoriesFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentStoriesBinding
    private var currentStory: Story? = null
    private lateinit var textToSpeech: TextToSpeech


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
            val text = currentStory?.story_description
            if (text?.isNotEmpty() == true) {
                speakOut(text)
            } else {
                Toast.makeText(requireContext(), "No text to read", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun populateStory() {
        if (currentStory != null) {
            binding.tvStory.text = currentStory?.story_description
            binding.ivStoryImage.loadImage(currentStory?.story_image, R.drawable.no_image_found_placeholder)
        }
    }

    private fun init() {
        currentStory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("story", Story::class.java)
        } else {
            arguments?.getSerializable("story") as? Story
        }
        setUpToolBar()
        animateViews()
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
            requireActivity().title= resources.getString(R.string.story)
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(requireContext(), "Language not supported", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(requireContext(), "Initialization failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    private fun speakOut(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}