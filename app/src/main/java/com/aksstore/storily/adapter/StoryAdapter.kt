package com.aksstore.storily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aksstore.storily.R
import com.aksstore.storily.databinding.StoryListItemBinding
import com.aksstore.storily.model.Story
import com.aksstore.storily.utils.loadImageWithThumb


class StoryAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<StoryAdapter.ItemViewHolder>() {

    private val storyList = mutableListOf<Story>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val binding =
            StoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(storyList[position])
    }

    fun setData(storyList: List<Story>) {
        this.storyList.removeAll(this.storyList)
        this.storyList.clear()
        notifyDataSetChanged()
        this.storyList.addAll(storyList)
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(private val binding: StoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            binding.tvStoryTitle.text = item.story_title
            binding.tvStoryDescription.text = item.story_name
            binding.ivStoryImage.loadImageWithThumb(item.story_image, R.drawable.ic_loading)

            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(
                    item
                )
            }
            binding.executePendingBindings()
        }
    }


    interface OnItemClickListener {
        fun onItemClick(item: Story?)
    }

}

