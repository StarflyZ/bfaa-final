package com.steven.submissionawal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.steven.submissionawal.database.entity.FavoriteUser
import com.steven.submissionawal.database.FavoriteRepository

class DaoViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    fun insertFavUser(username: FavoriteUser){
        favoriteRepository.setFavUser(username)
    }
    fun deleteFavoriteUser(username: String){
        favoriteRepository.deleteFavUser(username)
    }
}