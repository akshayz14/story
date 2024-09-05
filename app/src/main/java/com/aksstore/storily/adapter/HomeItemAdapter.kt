package com.aksstore.storily.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aksstore.storily.R
import com.aksstore.storily.StorilyApplication
import com.aksstore.storily.databinding.CardItemLayoutBinding
import com.aksstore.storily.model.home.HomeModelItem
import com.aksstore.storily.utils.isNightMode

class HomeItemAdapter(private val onItemClickListener: OnHomeItemClickListener) :
    RecyclerView.Adapter<HomeItemAdapter.ItemViewHolder>() {

    private val list = mutableListOf<HomeModelItem>()
    private val mContext = StorilyApplication.getAppContext()

    inner class ItemViewHolder(private val binding: CardItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeModelItem) {
            binding.tvStoriesAbout.text = item.story_title
            binding.tvStoriesAboutDescription.text = item.story_desc

            if (isNightMode(mContext)) {
                binding.tvStoriesAbout.setTextColor(
                    mContext.resources.getColor(R.color.white, null)
                )
                binding.tvStoriesAboutDescription.setTextColor(
                    mContext.resources.getColor(R.color.white, null)
                )
            } else {
                binding.tvStoriesAbout.setTextColor(
                    mContext.resources.getColor(R.color.black, null)
                )
                binding.tvStoriesAboutDescription.setTextColor(
                    mContext.resources.getColor(R.color.black, null)
                )
            }

            val drawableName = item.story_image
            val resourceId =
                mContext.resources.getIdentifier(drawableName, "drawable", mContext.packageName)

            if (resourceId != 0) {
                binding.ivStoriesType.setImageResource(resourceId)
            } else {
                binding.ivStoriesType.setImageResource(R.drawable.no_image_found_placeholder)
                Log.e("ImageView", "Drawable not found")
            }

            binding.root.setOnClickListener {
                onItemClickListener.onHomeItemClick(
                    item
                )
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeItemAdapter.ItemViewHolder {
        val binding =
            CardItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ItemViewHolder(binding)

    }

    fun setData(storyList: List<HomeModelItem>) {
        this.list.removeAll(this.list)
        this.list.clear()
        notifyDataSetChanged()
        this.list.addAll(storyList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeItemAdapter.ItemViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnHomeItemClickListener {
        fun onHomeItemClick(item: HomeModelItem?)
    }

}