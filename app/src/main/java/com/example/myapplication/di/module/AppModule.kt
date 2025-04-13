package com.example.myapplication.di.module

import com.example.myapplication.BuildConfig
import com.example.myapplication.repository.GithubRepository
import com.example.myapplication.service.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideRepository(apiService: ApiService): GithubRepository {
        return GithubRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create<ApiService>()

}