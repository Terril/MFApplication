package com.example.myapplication.di.module

import com.example.myapplication.GithubUserDetailFragment
import com.example.myapplication.GithubUserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun contributeGithubUserListFragment(): GithubUserListFragment


    @ContributesAndroidInjector
    abstract fun contributeGithubUserDetailFragment(): GithubUserDetailFragment
}