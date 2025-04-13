package com.example.myapplication

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.data.GithubUser
import com.example.myapplication.viewmodel.GithubViewModel
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


@RunWith(AndroidJUnit4::class)
class GithubUserListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(EmptyTestActivity::class.java)

    private lateinit var scenario: FragmentScenario<GithubUserListFragment>

    @Before
    fun setUp() {
        val testViewModelFactory = mock(ViewModelProvider.Factory::class.java)
        val testViewModel = mock(GithubViewModel::class.java)
        `when`(testViewModelFactory.create(GithubViewModel::class.java)).thenReturn(testViewModel)

        // Launch the fragment manually
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_MyApplication) {
            GithubUserListFragment().apply {
                viewModelFactory = testViewModelFactory
            }
        }
    }

    @Test
    fun testRecyclerViewDisplayed() {
        onView(withId(R.id.user_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchInputUpdatesList() {
        val dummyUsers = listOf(
            GithubUser.Item(
                id = 1,
                login = "john_doe",
                avatar_url = "https://example.com/john.jpg"
            ),
            GithubUser.Item(id = 2, login = "jane_doe", avatar_url = "")
        )

        scenario.onFragment {
            val adapter = it.userListAdapter
            adapter.submitFullList(dummyUsers)
        }

        onView(withId(R.id.search_view)).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> = isAssignableFrom(SearchView::class.java)
            override fun getDescription(): String = "Set query on SearchView"
            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery("john", true)
            }
        })

        onView(withText("john_doe")).check(matches(isDisplayed()))
    }
}
