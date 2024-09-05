package com.aksstore.storily

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aksstore.storily.adapter.HomeItemAdapter
import com.aksstore.storily.databinding.FragmentHome2Binding
import com.aksstore.storily.model.home.HomeModel
import com.aksstore.storily.model.home.HomeModelItem
import com.aksstore.storily.utils.readAssetsFile
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment2 : Fragment(), HomeItemAdapter.OnHomeItemClickListener {

    private lateinit var homeAdapter: HomeItemAdapter
    private lateinit var binding: FragmentHome2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHome2Binding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val list: List<HomeModelItem> = loadHomeListFromJson()
        homeAdapter = HomeItemAdapter(this)
        binding.rvHome.adapter = homeAdapter
        homeAdapter.setData(list)
    }

    private fun loadHomeListFromJson(): List<HomeModelItem> {
        val moduleJson: String = requireContext().assets.readAssetsFile("home" + ".json")
        return Gson().fromJson(moduleJson, HomeModel::class.java)
    }

    override fun onHomeItemClick(item: HomeModelItem?) {
        Log.d("TAG", "onHomeItemClick: $item")

        item?.name?.let {
            navigateToListPage(it)
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