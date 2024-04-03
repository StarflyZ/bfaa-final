package com.steven.submissionawal.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.steven.submissionawal.data.response.ItemsItem
import com.steven.submissionawal.databinding.ItemRowBinding
import com.steven.submissionawal.ui.main.DetailActivity

class UsersAdapter : ListAdapter<ItemsItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            val intentDetail = Intent(context, DetailActivity::class.java).apply {
                putExtra("username", users.login)
                putExtra("avatarUrl", "https://avatars.githubusercontent.com/u/${users.id}?v=4")
            }
            context.startActivity(intentDetail)
        }
    }

    class MyViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ItemsItem){
            binding.tvName.text = "${users.login}"
            Glide.with(binding.ivProfile.context)
                .load("https://avatars.githubusercontent.com/u/${users.id}?v=4")
                .into(binding.ivProfile)
        }
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