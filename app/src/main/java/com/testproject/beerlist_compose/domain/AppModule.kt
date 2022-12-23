package com.testproject.beerlist_compose.domain


import com.google.gson.Gson
import com.testproject.beerlist_compose.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//dependacy will live for the whole application

object AppModule {


    @Provides
    @Singleton
    //scope : single instance at all times
    fun provideRetrofit() : Retrofit{
        //gson object and type creation(we use it in main)
        val gson = Gson()
        val base_url = "https://api.punkapi.com/v2/"

        val retrofit: Retrofit by lazy {
            Retrofit.Builder().
            baseUrl(base_url).
            addConverterFactory(GsonConverterFactory.create(gson)).
            build()
        }
        return retrofit
    }


    @Provides
    @Singleton
    //scope : single instance at all times
    fun provideApiInterface(retrofit: Retrofit) : ApiService {
        val apiService : ApiService = retrofit.create(ApiService::class.java)
        return apiService
    }

    @Provides
    @Singleton
    //scope : single instance at all times
    fun provideRepository(apiService: ApiService): Lazy<Repository> {
        return lazy { Repository(apiService) }
    }
}