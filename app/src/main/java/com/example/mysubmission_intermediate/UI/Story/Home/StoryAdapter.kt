package com.example.mysubmission_intermediate.UI.Story.Home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmission_intermediate.Api.StoryResponseItem
import com.example.mysubmission_intermediate.R
import com.example.mysubmission_intermediate.UI.Story.Detail.DetailActivity
import com.example.mysubmission_intermediate.UI.Story.Detail.DetailActivity.Companion.EXTRA_DATA
import com.example.mysubmission_intermediate.databinding.ItemListStoryBinding

class StoryAdapter : PagingDataAdapter<StoryResponseItem, StoryAdapter.ListStoryViewHolder>(mDiffCallback) {


    inner class ListStoryViewHolder(private val binding: ItemListStoryBinding) :
       RecyclerView.ViewHolder(binding.root) {
       fun bind(story: StoryResponseItem){
           binding.apply {
               tvUsername.text = story.name
               tvDescription.text = story.description
               Glide.with(itemView.context)
                   .load(story.photoUrl)
                   .fitCenter()
                   .apply(
                       RequestOptions
                           .placeholderOf(R.drawable.ic_baseline_refresh_24)
                           .error(R.drawable.ic_baseline_broken_image_24)
                   )
                   .into(imgStory)
           }
           itemView.setOnClickListener {
               val intent = Intent(itemView.context, DetailActivity::class.java)
               intent.putExtra(EXTRA_DATA, story)
               itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
           }
         }
       }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListStoryViewHolder {
        val binding = ItemListStoryBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ListStoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListStoryViewHolder, position: Int) {
        getItem(position)?.let{ holder.bind(it)}
    }

    companion object {
        val mDiffCallback = object : DiffUtil.ItemCallback<StoryResponseItem>(){
            override fun areItemsTheSame(
                oldItem: StoryResponseItem,
                newItem: StoryResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: StoryResponseItem,
                newItem: StoryResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}