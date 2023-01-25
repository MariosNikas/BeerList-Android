package com.testproject.beerlist_compose.data


import com.testproject.beerlist_compose.domain.Beer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("beers")
    suspend fun fetchBeers(@Query("page") page: String): Response<List<Beer>>

    @GET("beers/{id}")
    suspend fun fetchBeer(@Path("id") id: String): Response<List<Beer>>

    @GET("beers")
    suspend fun searchBeers(
        @Query("brewed_after") dateFrom: String? = "01-1700",
        @Query("brewed_before") dateTo: String? = "01-2300",
        @Query("page") page: String
    ): Response<List<Beer>>
}