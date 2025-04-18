package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GithubApplication : Application(), HasAndroidInjector {
        @Inject
        lateinit var androidInjector: DispatchingAndroidInjector<Any>
        override fun onCreate() {
            super.onCreate()
            DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
        }
        override fun androidInjector(): AndroidInjector<Any> = androidInjector
    }