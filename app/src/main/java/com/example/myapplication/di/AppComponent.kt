package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.GithubApplication
import com.example.myapplication.di.module.ActivityBindingModule
import com.example.myapplication.di.module.AppModule
import com.example.myapplication.di.module.FragmentBindingModule
import com.example.myapplication.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    ViewModelModule::class,
    FragmentBindingModule::class,
    ActivityBindingModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: GithubApplication)
}