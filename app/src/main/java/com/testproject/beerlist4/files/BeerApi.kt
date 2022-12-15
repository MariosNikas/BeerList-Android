package com.testproject.beerlist4.files


import com.testproject.beerlist4.files.Beer
import retrofit2.Call
import retrofit2.http.GET
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
    fun fetchBeers(@Query("page")page:String) : Call<List<Beer>>
}