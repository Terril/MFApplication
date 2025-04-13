package com.example.myapplication.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.di.SavedStateViewModelFactory
import com.example.myapplication.di.ViewModelFactory
import com.example.myapplication.di.ViewModelKey
import com.example.myapplication.viewmodel.GithubViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GithubViewModel::class)
    abstract fun bindGithubViewModel(viewModel: GithubViewModel): ViewModel
}