package com.example.myapplication

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class GithubUserDetailFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(EmptyTestActivity::class.java)

    @Test
    fun testGithubUserDetailFragment_displaysUserInfo() {
        val args = bundleOf("username" to "octocat")

        launchFragmentInContainer<GithubUserDetailFragment>(
            fragmentArgs = args,
            themeResId = R.style.Theme_MyApplication
        )

        // Add delay to wait for async load
        Thread.sleep(2000)

        // Check if basic views are displayed
        onView(withId(R.id.detailImage)).check(matches(isDisplayed()))
        onView(withId(R.id.detailName)).check(matches(isDisplayed()))
        onView(withId(R.id.detailUsername)).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewScrolls() {
        val args = bundleOf("username" to "octocat")

        launchFragmentInContainer<GithubUserDetailFragment>(
            fragmentArgs = args,
            themeResId = R.style.Theme_MyApplication
        )

        Thread.sleep(3000)

        onView(withId(R.id.repositoriesRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }
}
