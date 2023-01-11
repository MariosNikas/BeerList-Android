package com.testproject.beerlist_compose.data



import com.testproject.beerlist_compose.domain.Beer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//object ApiClient {
//
//    val listPersonType = object : TypeToken<List<Beer>>() {}.type
//
//    //val result = adapter.fromJson("beerList.json")
//
//    //retrofit object. I pass the url and add the moshi converter using the moshi object i created before
////    private val retrofit: Retrofit by lazy {
////        Retrofit.Builder().
////        baseUrl(base_url).
////        addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient.build()).
////        build()
////    }
//}


interface ApiService {
    //get request with to the path /beers where the records of the  beers are
    @GET("beers")
    //i can query the specific page of the list i want to get
    suspend fun fetchBeers(@Query("page")page:String) : Response<List<Beer>>
    @GET("beers/{id}")
    //i can query the specific page of the list i want to get
    suspend fun fetchBeer(@Path("id")id:Int) : Response<Beer>
}