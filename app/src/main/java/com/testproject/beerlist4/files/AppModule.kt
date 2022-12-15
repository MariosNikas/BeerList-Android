package com.testproject.beerlist4.files


import com.google.gson.Gson
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
        //url for the api that retrofit will use to collect the beer data
        val base_url = "https://api.punkapi.com/v2/"
        //val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        //var httpClient = OkHttpClient.Builder().addInterceptor(logging)
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
    fun provideRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }
}