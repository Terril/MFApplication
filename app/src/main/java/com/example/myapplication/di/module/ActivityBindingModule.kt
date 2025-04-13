package com.example.myapplication.di.module

import com.example.myapplication.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun contributeMainActivity(): MainActivity
}