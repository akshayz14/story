package com.aksstore.storily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aksstore.storily.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        setListener()

        return binding.root
    }

    private fun setListener() {

        binding.btnZeroToThree.setOnClickListener {
            navigateToListPage("ZERO_TO_THREE")
        }

        binding.btnThreeToTen.setOnClickListener {
            navigateToListPage("THREE_TO_TEN")
        }

        binding.btnTenToFifteen.setOnClickListener {
            navigateToListPage("TEN_PLUS")
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