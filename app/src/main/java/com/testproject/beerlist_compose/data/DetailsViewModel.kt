package com.testproject.beerlist_compose.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.testproject.beerlist_compose.domain.Beer
import com.testproject.beerlist_compose.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor (private val repository: Lazy<Repository>) : ViewModel(){
    // it will post updates when there is a change

    val id = 0
    private var _beer = MutableLiveData<Beer>()
    val beer : LiveData<Beer> get() = _beer
    suspend fun getBeer (beerId: Int) {
        fetchBeer(repository,_beer, beerId)
    }

}

private suspend fun fetchBeer(
    repository: Lazy<Repository>,
    beer: MutableLiveData<Beer>,
    beerId: Int
    )
{
    val response = repository.value.getBeer(beerId)
    if (response.isSuccessful) {
        Log.d("response", response.body().toString())
        beer.postValue(response.body())
    } else {
        Log.d("error", response.message())
    }

}

