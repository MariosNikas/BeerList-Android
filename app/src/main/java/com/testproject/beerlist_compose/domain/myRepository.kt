package com.testproject.beerlist_compose.domain


import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.testproject.beerlist_compose.UiClasses.BeerSource
import com.testproject.beerlist_compose.data.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {
    //suspend fun getBeers () = Pager(config = PagingConfig(pageSize = 25), pagingSourceFactory = {BeerSource(apiService)}).flow
    suspend fun getBeer (id : Int ) = apiService.fetchBeer(id.toString())
}