package com.aksstore.storily

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ScrollView
import com.aksstore.storily.databinding.FragmentStoriesBinding

class ReadStoriesFragment : Fragment() {

    private lateinit var binding: FragmentStoriesBinding
    private lateinit var storyDescription: String


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
        if (storyDescription.isNotEmpty()) {
            binding.tvStory.text = storyDescription
        }
    }

    private fun init() {
        storyDescription = arguments?.getString("storyDescription", "") ?: ""
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

}