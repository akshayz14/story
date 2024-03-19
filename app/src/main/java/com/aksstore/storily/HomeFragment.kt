package com.aksstore.storily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aksstore.storily.databinding.FragmentHomeBinding
import com.aksstore.storily.utils.dpToPx

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initView()
        return binding.root
    }

    private fun initView() {

        for (i in 0 until 6) {
            val cardView = layoutInflater.inflate(R.layout.card_item_layout, null) as CardView
            val ivStoriesType = cardView.findViewById<ImageView>(R.id.ivStoriesType)

            val layoutParams = GridLayout.LayoutParams()
            layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            layoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            layoutParams.width = 0
            layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT
            layoutParams.setMargins(15, 15, 15, 15)
            cardView.layoutParams = layoutParams
            if (i == 0) {
                ivStoriesType.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.zero_to_three_home
                    )
                )
            }

            identifyAndSetCardDetails(i, cardView)
            cardView.cardElevation = 4.dpToPx(resources).toFloat()
            cardView.radius = 8.dpToPx(resources).toFloat()
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple_light
                )
            )

            cardView.setOnClickListener {
                callClickListener(i)
            }

            binding.gridLayout.addView(cardView)
        }

    }

    private fun identifyAndSetCardDetails(i: Int, cardView: CardView) {

        when (i) {
            0 -> {
                setupUIForCardView(
                    R.drawable.zero_to_three_home,
                    resources.getString(R.string.zeroToThreeYears),
                    resources.getString(R.string.stories_for_0_to_3_years_text),
                    cardView
                )
            }

            1 -> {
                setupUIForCardView(
                    R.drawable.three_to_ten_home,
                    resources.getString(R.string.threeToTen),
                    resources.getString(R.string.stories_for_3_to_10_years),
                    cardView
                )
            }

            2 -> {
                setupUIForCardView(
                    R.drawable.ten_plus_home,
                    resources.getString(R.string.tenToFifteenYears),
                    resources.getString(R.string.stories_for_all_ages),
                    cardView
                )
            }

            3 -> {
                setupUIForCardView(
                    R.drawable.indian_mythology_home,
                    resources.getString(R.string.indian_mythology),
                    resources.getString(R.string.indian_mythology_story_description),
                    cardView
                )
            }

            4 -> {
                setupUIForCardView(
                    R.drawable.inspirational_home,
                    resources.getString(R.string.inspirational_stories),
                    resources.getString(R.string.inspirational_stories_description),
                    cardView
                )
            }

            5 -> {
                setupUIForCardView(
                    R.drawable.miscellaneous_home,
                    resources.getString(R.string.miscellaneous),
                    resources.getString(R.string.miscellaneous_description),
                    cardView
                )
            }
        }

    }

    private fun setupUIForCardView(
        drawable: Int,
        tvStoriesAboutText: String,
        tvStoriesAboutDescriptionText: String,
        cardView: CardView
    ) {
        val tvStoriesAbout = cardView.findViewById<TextView>(R.id.tvStoriesAbout)
        val tvStoriesAboutDescription =
            cardView.findViewById<TextView>(R.id.tvStoriesAboutDescription)
        val ivStoriesType = cardView.findViewById<ImageView>(R.id.ivStoriesType)

        tvStoriesAbout.text = tvStoriesAboutText
        tvStoriesAboutDescription.text = tvStoriesAboutDescriptionText
        ivStoriesType.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                drawable
            )
        )
    }

    private fun callClickListener(i: Int) {

        when (i) {
            0 -> {
                navigateToListPage("ZERO_TO_THREE")
            }

            1 -> {
                navigateToListPage("THREE_TO_TEN")
            }

            2 -> {
                navigateToListPage("TEN_PLUS")
            }

            3 -> {
                navigateToListPage("MYTHOLOGY")
            }

            4 -> {
                navigateToListPage("INSPIRATIONAL")
            }

            5 -> {
                navigateToListPage("MISCELLANEOUS")
            }
        }

    }


    private fun navigateToListPage(moduleName: String) {
        val args = Bundle().apply {
            putString("moduleName", moduleName)
        }
        findNavController().navigate(
            R.id.action_homeFragment_to_storiesListFragment,
            args
        )
    }
}