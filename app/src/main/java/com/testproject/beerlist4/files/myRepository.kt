package com.testproject.beerlist4.files


import com.testproject.beerlist4.files.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getBeers (page : String ) = apiService.fetchBeers(page)
}