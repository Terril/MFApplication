package com.example.myapplication.service

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.GithubUser
import com.example.myapplication.data.GithubUserDetail
import com.example.myapplication.data.GithubUserRepos
import retrofit2.http.*

interface ApiService {
    companion object {
        var BASE_URL = "Constants.apiURL"
    }

    @Headers("Accept: application/json")
//    @GET("/search/repositories")
//    suspend fun getSearchResult(
//        @Query("q") query: String,
//        @Query("") page: Int,
//        @Query("per_page") perPage: Int,
//        @Query("sort") sort:String,
//        @Query("order") order: String
//    ): Item

    @GET("users")
    suspend fun getGithubUser(@Header("Authorization") authorization: String = BuildConfig.TOKEN):
            List<GithubUser.Item>

    @GET("users/{username}")
    suspend fun getGithubUserDetail(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): GithubUserDetail

    @GET("users/{username}/followers")
    suspend fun getGithubUserFollowers(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): MutableList<GithubUser.Item>

    @GET("users/{username}/following")
    suspend fun getGithubUserFollowing(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): MutableList<GithubUser.Item>

    @GET("search/users")
    suspend fun getGithubUserSearch(
        @QueryMap param: Map<String, Any>,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): GithubUser

    @GET("users/{username}/repos")
    suspend fun getGithubUserRepos(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): List<GithubUserRepos>

}