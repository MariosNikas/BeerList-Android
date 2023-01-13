package com.testproject.beerlist_compose.domain


import com.testproject.beerlist_compose.data.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getBeers (page : String ) = apiService.fetchBeers(page)
    suspend fun getBeer (id : Int ) = apiService.fetchBeer(id.toString())
}