package com.example.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.data.GithubUser
import com.example.myapplication.data.GithubUserDetail
import com.example.myapplication.data.GithubUserRepos
import com.example.myapplication.repository.GithubRepository
import com.example.myapplication.result.Result
import com.example.myapplication.viewmodel.GithubViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GithubViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: GithubRepository

    private lateinit var viewModel: GithubViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = GithubViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getGithubUsers emits success`() = runTest {
        val users = listOf(
            GithubUser.Item(id = 1, login = "user1", avatar_url = ""),
            GithubUser.Item(id = 2, login = "user2", avatar_url = "")
        )
        `when`(repository.getGithubUser()).thenReturn(flow {
            emit(Result.Success(users))
        })

        viewModel.getGithubUsers()

        // small delay to allow coroutine to emit value
        kotlinx.coroutines.delay(100)

        assertEquals(Result.Success(users), viewModel.userState.value)
    }

    @Test
    fun `getGithubUserDetail emits detail and repos`() = runTest {
        val username = "user1"
        val detail = GithubUserDetail("user1", "bio")
        val repos = listOf(GithubUserRepos(1, name = "Repo1", fork = false))

        `when`(repository.getGithubUserDetail(username)).thenReturn(flow {
            emit(Result.Success(detail))
        })

        `when`(repository.getGithubUserRepos(username)).thenReturn(flow {
            emit(Result.Success(repos))
        })

        viewModel.getGithubUserDetail(username)

        kotlinx.coroutines.delay(100)

        assertEquals(Result.Success(detail), viewModel.userDetailState.value)
        assertEquals(Result.Success(repos), viewModel.userReposState.value)
    }

    @Test
    fun `filterUserBasedOnForks returns only non-forked repos`() {
        val repos = listOf(
            GithubUserRepos(name = "Repo1", fork = false),
            GithubUserRepos(name = "Repo2", fork = true)
        )

        val filtered = viewModel.filterUserBasedOnForks(repos)

        assertEquals(1, filtered.size)
        assertEquals("Repo1", filtered[0].name)
    }
}
