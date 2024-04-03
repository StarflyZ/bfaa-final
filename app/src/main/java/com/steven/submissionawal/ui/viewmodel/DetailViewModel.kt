package com.steven.submissionawal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.steven.submissionawal.data.response.DetailUserResponse
import com.steven.submissionawal.data.response.ItemsItem
import com.steven.submissionawal.data.retrofit.ApiConfig
import com.steven.submissionawal.database.FavoriteRepository
import com.steven.submissionawal.database.entity.FavoriteUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

    companion object{
        private const val TAG = "DetailActivity"
    }

    fun showFollows(username: String){
        val detail = ApiConfig.getApiService().getDetailUser(username)
        detail.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if(response.isSuccessful){
                    _detailUser.value = response.body()
                }else{
                    Log.e(TAG, "onFailure(showFollows1): ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure(showFollowsCall): ${t.message}")
            }
        })
    }

    fun getFollowers(username: String){
        _isLoading.value = true
        val followers = ApiConfig.getApiService().getFollowers(username)
        followers.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listFollowers.value = response.body()
                }else{
                    Log.e(TAG, "onFailure(getFollower1): ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure(getFollowerCall): ${t.message}")
            }
        })
    }

    fun getFollowing(username: String){
        _isLoading.value = true
        val following = ApiConfig.getApiService().getFollowing(username)
        following.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listFollowing.value = response.body()
                }else{
                    Log.e(TAG, "onFailure(getFollowing1): ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure(getFollowingCall): ${t.message}")
            }
        })
    }
}