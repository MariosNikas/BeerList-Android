package com.testproject.beerlist4.files

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
//private val repository : Repository = Repository(AppModule.apiService)
class MainViewModel @Inject constructor(private val repository : Repository): ViewModel() {
    // it will post updates when there is a change
    private var _beerList = MutableLiveData<List<Beer>> ()
    // mutable live data list can only be observed not changed
    val beerList : LiveData<List<Beer>> get() = _beerList

    //call fatchbeer to make the api call using the repository class
    init{
        fetchBeer(repository, _beerList)
    }


}

private fun fetchBeer(repository: Repository, beerList :MutableLiveData<List<Beer>> ){
    val client =repository.getBeers("1")
    client.enqueue(object :retrofit2.Callback<List<Beer>>{
        /**
         * Invoked for a received HTTP response.
         *
         *
         * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
         * Call [Response.isSuccessful] to determine if the response indicates success.
         */
        override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
            if (response.isSuccessful){
                Log.d("response",response.body().toString())
                beerList.postValue(response.body())
            }
            else{
                Log.d("error", response.message())
            }
        }

        /**
         * Invoked when a network exception occurred talking to the server or when an unexpected exception
         * occurred creating the request or processing the response.
         */
        override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
            Log.d("failure", t.message.toString())
        }

    })
}