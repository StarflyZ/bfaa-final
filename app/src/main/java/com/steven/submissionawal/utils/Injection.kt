package com.steven.submissionawal.utils

import android.content.Context
import com.steven.submissionawal.data.retrofit.ApiConfig
import com.steven.submissionawal.database.FavoriteRepository
import com.steven.submissionawal.database.room.FavoriteRoomDatabase
import com.steven.submissionawal.ui.viewmodel.DaoViewModelFactory

object Injection {
    private fun provideAppExecutors(): AppExecutors{
        return AppExecutors()
    }
    private fun provideDatabase(context: Context): FavoriteRoomDatabase{
        return FavoriteRoomDatabase.getDatabase(context)
    }
    fun provideFavoriteRepository(context: Context): FavoriteRepository{
        val apiService = ApiConfig.getApiService()
        val db = provideDatabase(context)
        val favoriteDao = db.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository(apiService, favoriteDao, appExecutors)
    }

    fun provideViewModelFactory(context: Context): DaoViewModelFactory{
        val repository = provideFavoriteRepository(context)
        return DaoViewModelFactory(repository)
    }
}