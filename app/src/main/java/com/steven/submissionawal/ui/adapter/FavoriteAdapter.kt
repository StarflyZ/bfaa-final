package com.steven.submissionawal.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.steven.submissionawal.data.response.ItemsItem
import com.steven.submissionawal.database.entity.FavoriteUser
import com.steven.submissionawal.databinding.ItemFavRowBinding
import com.steven.submissionawal.helper.FavoriteDiffCallback
import com.steven.submissionawal.ui.main.DetailActivity

class FavoriteAdapter : ListAdapter<FavoriteUser, FavoriteAdapter.FavoriteViewHolder>(DiffCallback()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val binding = ItemFavRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val curentItem = getItem(position)
        holder.bind(curentItem)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intentDetail = Intent(context, DetailActivity::class.java).apply {
                putExtra("username", curentItem.username)
                putExtra("avatarUrl", curentItem.avatarUrl)
            }
            context.startActivity(intentDetail)
        }
    }

    class FavoriteViewHolder(private val binding: ItemFavRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteUser: FavoriteUser){
            with(binding){
                Glide.with(itemView)
                    .load(favoriteUser.avatarUrl)
                    .into(ivFavProfile)
                tvFavName.text = favoriteUser.username
            }
        }
    }
    class DiffCallback : DiffUtil.ItemCallback<FavoriteUser>() {
        override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
            return oldItem.username == newItem.username
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
            return oldItem == newItem
        }
    }
}