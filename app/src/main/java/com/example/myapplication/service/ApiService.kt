package com.example.myapplication.service

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.GithubUser
import com.example.myapplication.data.GithubUserDetail
import com.example.myapplication.data.GithubUserRepos
import retrofit2.http.*

interface ApiService {

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