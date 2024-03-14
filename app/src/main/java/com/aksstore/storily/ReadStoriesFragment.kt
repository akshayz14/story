package com.aksstore.storily

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aksstore.storily.databinding.FragmentStoriesBinding
import com.aksstore.storily.model.Story

class ReadStoriesFragment : Fragment() {

    private lateinit var binding: FragmentStoriesBinding
    private var currentStory: Story? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

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
    }

    private fun populateStory() {
        if (currentStory != null) {
            binding.tvStory.text = currentStory?.story_description
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

    private fun ScrollView.startAnimation() {
        val distance = 500 // distance to scroll in pixels
        val duration = 3000L // duration of animation in milliseconds

        // Scroll animation
        val animator = ObjectAnimator.ofInt(this, "scrollY", 0, distance)
        animator.duration = duration
        animator.interpolator = LinearInterpolator()
        animator.start()
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

    private fun setUpToolBar() {
        if (!currentStory?.story_title.isNullOrEmpty()) {
            activity?.title = currentStory?.story_title
        } else {
            activity?.title = resources.getString(R.string.story)
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}