package com.aksstore.storily

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aksstore.storily.databinding.FragmentHomeBinding
import com.aksstore.storily.utils.AppConstants
import com.aksstore.storily.utils.dpToPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    }
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

        for (i in 0 until 15) {
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

            val dialogShown = sharedPreferences.getBoolean("dialogShown", false)
            if (!dialogShown) {
                showOneTimeDialog()
                // Update SharedPreferences to indicate that the dialog has been shown
                sharedPreferences.edit().putBoolean("dialogShown", true).apply()
            }


            binding.gridLayout.addView(cardView)
        }

    }

    private fun showOneTimeDialog() {
        val dialog = Dialog(requireContext(), R.style.Theme_Dialog)
        dialog.setContentView(R.layout.custom_dialog)

        val tvDialogTitle = dialog.findViewById<TextView>(R.id.tvDialogTitle)
        val tvDialogDescription = dialog.findViewById<TextView>(R.id.tvDialogDescription)

        tvDialogTitle.text = resources.getString(R.string.welcome)
        tvDialogDescription.text = resources.getString(R.string.welcome_description)

        val closeButton = dialog.findViewById<Button>(R.id.dialogButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()



    }

    private fun identifyAndSetCardDetails(i: Int, cardView: CardView) {

        when (i) {
            AppConstants.KIDS_STORIES -> {
                setupUIForCardView(
                    R.drawable.zero_to_three_home,
                    resources.getString(R.string.kids_stories),
                    resources.getString(R.string.stories_for_kids_description),
                    cardView
                )
            }

            AppConstants.MYTHOLOGICAL_STORIES -> {
                setupUIForCardView(
                    R.drawable.indian_mythology_home,
                    resources.getString(R.string.indian_mythology),
                    resources.getString(R.string.indian_mythology_story_description),
                    cardView
                )
            }

            AppConstants.INSPIRATIONAL_STORIES -> {
                setupUIForCardView(
                    R.drawable.inspirational_home,
                    resources.getString(R.string.inspirational_stories),
                    resources.getString(R.string.inspirational_stories_description),
                    cardView
                )
            }

            AppConstants.MOTIVATIONAL_STORIES -> {
                setupUIForCardView(
                    R.drawable.motivational_home,
                    resources.getString(R.string.motivational_stories),
                    resources.getString(R.string.motivational_stories_description),
                    cardView
                )
            }

            AppConstants.SCIENCE_STORIES -> {
                setupUIForCardView(
                    R.drawable.science_home,
                    resources.getString(R.string.science_stories),
                    resources.getString(R.string.science_stories_description),
                    cardView
                )
            }

            AppConstants.PLANETS_STORIES -> {
                setupUIForCardView(
                    R.drawable.planets_home,
                    resources.getString(R.string.planet_stories),
                    resources.getString(R.string.planet_stories_description),
                    cardView
                )
            }

            AppConstants.ADVENTURE_STORIES -> {
                setupUIForCardView(
                    R.drawable.adventure_home,
                    resources.getString(R.string.adventure_stories),
                    resources.getString(R.string.adventure_stories_description),
                    cardView
                )
            }

            AppConstants.PRINCESS_STORIES -> {
                setupUIForCardView(
                    R.drawable.princess_home,
                    resources.getString(R.string.princess_stories),
                    resources.getString(R.string.princess_stories_description),
                    cardView
                )
            }


            AppConstants.AKBAR_BIRBAL -> {
                setupUIForCardView(
                    R.drawable.akbar_birbal,
                    resources.getString(R.string.akbar_birbal),
                    resources.getString(R.string.exciting_stories_from_akbar_birbal),
                    cardView
                )
            }

            AppConstants.FOLKTALE_STORIES -> {
                setupUIForCardView(
                    R.drawable.folktales_home,
                    resources.getString(R.string.folktale_stories),
                    resources.getString(R.string.folktale_stories_description),
                    cardView
                )
            }

            AppConstants.JOKES_STORIES -> {
                setupUIForCardView(
                    R.drawable.jokes_home,
                    resources.getString(R.string.jokes_stories),
                    resources.getString(R.string.jokes_stories_description),
                    cardView
                )
            }

            AppConstants.POEMS -> {
                setupUIForCardView(
                    R.drawable.poem_home,
                    resources.getString(R.string.poem_stories),
                    resources.getString(R.string.poems_stories_description),
                    cardView
                )
            }
            AppConstants.FRIENDSHIP_STORIES -> {
                setupUIForCardView(
                    R.drawable.ten_home,
                    resources.getString(R.string.friendship_stories),
                    resources.getString(R.string.friendship_stories_description),
                    cardView
                )
            }
            AppConstants.TENALI_RAM_STORIES -> {
                setupUIForCardView(
                    R.drawable.tenali_rama_home,
                    resources.getString(R.string.tr_stories),
                    resources.getString(R.string.tenaliram_stories_description),
                    cardView
                )
            }
            AppConstants.ARABIAN_NIGHTS_STORIES -> {
                setupUIForCardView(
                    R.drawable.arabian_nights_home,
                    resources.getString(R.string.arabian_night_stories),
                    resources.getString(R.string.arabian_night_stories_description),
                    cardView
                )
            }
            AppConstants.CHILDHOOD_STORIES -> {
                setupUIForCardView(
                    R.drawable.three_to_ten_home,
                    resources.getString(R.string.childhood_stories),
                    resources.getString(R.string.childhood_stories_description),
                    cardView
                )
            }

            AppConstants.MISCELLANEOUS_STORIES -> {
                setupUIForCardView(
                    R.drawable.miscellaneous_home,
                    resources.getString(R.string.miscellaneous),
                    resources.getString(R.string.miscellaneous_description),
                    cardView
                )
            }

            AppConstants.GRANDMA_STORIES -> {
                setupUIForCardView(
                    R.drawable.grandma2,
                    resources.getString(R.string.grandma_stories),
                    resources.getString(R.string.grandma_stories_description),
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
            AppConstants.KIDS_STORIES -> {
                navigateToListPage("KIDS")
            }


            AppConstants.MYTHOLOGICAL_STORIES -> {
                navigateToListPage("MYTHOLOGY")
            }

            AppConstants.INSPIRATIONAL_STORIES -> {
                navigateToListPage("INSPIRATIONAL")
            }


            AppConstants.MOTIVATIONAL_STORIES -> {
                navigateToListPage("MOTIVATIONAL")
            }


            AppConstants.SCIENCE_STORIES -> {
                navigateToListPage("SCIENCE")
            }


            AppConstants.PLANETS_STORIES -> {
                navigateToListPage("PLANETS")
            }


            AppConstants.ADVENTURE_STORIES -> {
                navigateToListPage("ADVENTURE")
            }


            AppConstants.PRINCESS_STORIES -> {
                navigateToListPage("PRINCESS")
            }


            AppConstants.AKBAR_BIRBAL -> {
                navigateToListPage("AKBAR_BIRBAL")
            }


            AppConstants.FOLKTALE_STORIES -> {
                navigateToListPage("FOLKTALE")
            }


            AppConstants.JOKES_STORIES -> {
                navigateToListPage("FUNNY")
            }


            AppConstants.POEMS -> {
                navigateToListPage("POEMS")
            }


            AppConstants.FRIENDSHIP_STORIES -> {
                navigateToListPage("FRIENDSHIP")
            }


            AppConstants.TENALI_RAM_STORIES -> {
                navigateToListPage("TENALI_RAM")
            }


            AppConstants.ARABIAN_NIGHTS_STORIES -> {
                navigateToListPage("ARABIAN_NIGHTS")
            }

            AppConstants.CHILDHOOD_STORIES -> {
                navigateToListPage("CHILDHOOD")
            }

            AppConstants.MISCELLANEOUS_STORIES -> {
                navigateToListPage("MISCELLANEOUS")
            }
            AppConstants.GRANDMA_STORIES -> {
                navigateToListPage("GRANDMA")
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