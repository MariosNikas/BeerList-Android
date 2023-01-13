package com.testproject.beerlist_compose.data



import com.testproject.beerlist_compose.domain.Beer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //get request with to the path /beers where the records of the  beers are
    @GET("beers")
    //i can query the specific page of the list i want to get
    suspend fun fetchBeers(@Query("page")page:String) : Response<List<Beer>>
    @GET("beers/{id}")
    //i can query the specific page of the list i want to get
    suspend fun fetchBeer(@Path("id")id:String) : Response<List<Beer>>
}