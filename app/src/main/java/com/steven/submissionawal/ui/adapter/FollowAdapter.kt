package com.steven.submissionawal.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.steven.submissionawal.data.response.ItemsItem
import com.steven.submissionawal.databinding.FollowRowBinding
import com.steven.submissionawal.ui.main.DetailActivity

class FollowAdapter : ListAdapter<ItemsItem, FollowAdapter.FollowViewHolder>(DIFF_CALLBACK) {

    class FollowViewHolder(private val binding: FollowRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem){
            with(binding){
                tvFollowName.text = "${item.login}"
                Glide.with(itemView.context)
                    .load("${item.avatarUrl}")
                    .into(ivFollowProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding = FollowRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}