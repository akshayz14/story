package com.aksstore.storily.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aksstore.storily.R
import com.aksstore.storily.adapter.StoryAdapter
import com.aksstore.storily.databinding.FragmentStoriesListBinding
import com.aksstore.storily.model.Story
import com.aksstore.storily.model.StoryModel
import com.aksstore.storily.utils.readAssetsFile
import com.aksstore.storily.viewmodel.StorilyViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoriesListFragment : Fragment(), StoryAdapter.OnItemClickListener {

    private lateinit var binding: FragmentStoriesListBinding

    private lateinit var moduleName: String
    private lateinit var storyAdapter: StoryAdapter

    private var storyList = mutableListOf<Story>()
    private val viewModel: StorilyViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoriesListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        init()
        if (!moduleName.isBlank()) {
            loadJsonFromViewModel(moduleName)
        }
    }

    private fun setupRecyclerView() {
        storyAdapter = StoryAdapter(this)
        binding.rvStoriesList.layoutManager = LinearLayoutManager(context)
        binding.rvStoriesList.adapter = storyAdapter
    }

    /**
     * Old way of loading data from assets folder
     *
     * */
    private fun loadJsonFromAssets(moduleName: String) {
        val moduleJson: String = requireContext().assets.readAssetsFile(moduleName + ".json")
        Log.d("TAG", "loadJsonFromAssets: " + moduleJson)

        if (moduleJson.isNotEmpty()) {
            val module = Gson().fromJson(moduleJson, StoryModel::class.java)
            storyList = module.stories.toMutableList()
            storyAdapter.setData(storyList)
        }
    }

    /**
     *
     * New way of loading data from viewmodel
     *
     * */
    private fun loadJsonFromViewModel(moduleName : String) {
        viewModel.loadData(moduleName + ".json")?.apply {
            storyList = this.stories.toMutableList()
            storyAdapter.setData(storyList)
        }
    }

    private fun init() {
        moduleName = arguments?.getString("moduleName", "") ?: ""
        Log.d("TAG", "init: " + moduleName)
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

    override fun onItemClick(item: Story?) {
        val args = Bundle().apply {
            putSerializable("story", item)
        }
        findNavController().navigate(
            R.id.action_storiesListFragment_to_storiesFragment,
            args
        )

    }

}