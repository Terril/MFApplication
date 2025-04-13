package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.GithubUser
import com.example.myapplication.data.GithubUserDetail
import com.example.myapplication.data.GithubUserRepos
import com.example.myapplication.repository.GithubRepository
import com.example.myapplication.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {


    private val _userState = MutableStateFlow<Result<List<GithubUser.Item>>>(Result.Loading)
    val userState: StateFlow<Result<List<GithubUser.Item>>> = _userState

    private val _userDetailState = MutableStateFlow<Result<GithubUserDetail>>(Result.Loading)
    val userDetailState: StateFlow<Result<GithubUserDetail>> = _userDetailState

    private val _userReposState = MutableStateFlow<Result<List<GithubUserRepos>>>(Result.Loading)
    val userReposState: StateFlow<Result<List<GithubUserRepos>>> = _userReposState

    fun getGithubUsers() {
        viewModelScope.launch {
            repository.getGithubUser().collect { result ->
                _userState.value = result
            }
        }
    }

    fun getGithubUserDetail(username: String) {
        viewModelScope.launch {
            combine(
                repository.getGithubUserDetail(username),
                repository.getGithubUserRepos(username)
            ) { userListResult, userDetailResult ->
                Pair(userListResult, userDetailResult)
            }.collect { (detail, repos) ->
                _userReposState.value = repos
                _userDetailState.value = detail
            }
        }
    }

    fun filterUserBasedOnForks(repos: List<GithubUserRepos>) : List<GithubUserRepos> {
        return repos.let {
            it.filter { repo ->
                repo.fork == false
            }
        }
    }

}