package com.steven.submissionawal.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.steven.submissionawal.data.retrofit.ApiService
import com.steven.submissionawal.database.room.FavoriteDao
import com.steven.submissionawal.database.entity.FavoriteUser
import com.steven.submissionawal.utils.AppExecutors
import com.steven.submissionawal.data.Result
import java.util.concurrent.Executor

class FavoriteRepository(
    private val apiService: ApiService,
    private val favsDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<FavoriteUser>>>()

    fun getFavUsers(): LiveData<List<FavoriteUser>>{
        return favsDao.getAllFavs()
    }

    fun setFavUser(user: FavoriteUser){
        appExecutors.diskIO.execute{
            favsDao.insert(user)
        }
    }

    fun deleteFavUser(username: String){
        appExecutors.diskIO.execute{
            favsDao.deleteFavoriteUserByUsername(username)
        }
    }


}