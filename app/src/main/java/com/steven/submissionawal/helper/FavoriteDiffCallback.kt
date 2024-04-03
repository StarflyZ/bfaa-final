package com.steven.submissionawal.helper

import androidx.recyclerview.widget.DiffUtil
import com.steven.submissionawal.database.entity.FavoriteUser

class FavoriteDiffCallback(private val oldFavList: List<FavoriteUser>, private val newFavList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size
    override fun getNewListSize(): Int = newListSize
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].username == newFavList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavList[oldItemPosition]
        val newFav = newFavList[newItemPosition]
        return oldFav.username == newFav.username && oldFav.avatarUrl == newFav.avatarUrl
    }
}