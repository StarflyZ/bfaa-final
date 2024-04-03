package com.steven.submissionawal.data.retrofit

import com.steven.submissionawal.data.response.DetailUserResponse
import com.steven.submissionawal.data.response.GithubResponse
import com.steven.submissionawal.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    //@Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    //@Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    //@Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    //@Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}