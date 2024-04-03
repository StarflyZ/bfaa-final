package com.steven.submissionawal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.steven.submissionawal.database.FavoriteRepository
import com.steven.submissionawal.database.entity.FavoriteUser

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _favoriteUsers = MutableLiveData<List<FavoriteUser>>()
    val favoriteUser: LiveData<List<FavoriteUser>> = _favoriteUsers

    init {
        getFavoriteUsers()
    }
    fun getFavoriteUsers(){
        _isLoading.value = true
        favoriteRepository.getFavUsers().observeForever { users ->
            _favoriteUsers.value = users
            _isLoading.value = false
        }
    }
}