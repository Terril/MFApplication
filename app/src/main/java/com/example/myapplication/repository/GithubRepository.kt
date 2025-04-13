package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.data.GithubUser
import com.example.myapplication.data.GithubUserDetail
import com.example.myapplication.data.GithubUserRepos
import com.example.myapplication.result.Result
import com.example.myapplication.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getGithubUser(): Flow<Result<List<GithubUser.Item>>> = flow {
        emit(Result.Loading)
        val response = apiService.getGithubUser()
        Log.d("TAG", response.toString())
        emit(Result.Success(response))
    }.catch { e ->
        emit(Result.Error(e))
    }


    fun getGithubUserDetail(username: String): Flow<Result<GithubUserDetail>> = flow {
        emit(Result.Loading)
        val response = apiService.getGithubUserDetail(username)
        Log.d("TAG", response.toString())
        emit(Result.Success(response))
    }.catch { e ->
        emit(Result.Error(e))
    }

    fun getGithubUserRepos(username: String): Flow<Result<List<GithubUserRepos>>> = flow {
        emit(Result.Loading)
        val response = apiService.getGithubUserRepos(username)
        Log.d("TAG", response.toString())
        emit(Result.Success(response))
    }.catch { e ->
        emit(Result.Error(e))
    }
}