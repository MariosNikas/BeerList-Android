package com.testproject.beerlist_compose.data

import android.util.Log
import androidx.lifecycle.*
import com.testproject.beerlist_compose.domain.Beer
import com.testproject.beerlist_compose.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor (private val repository: Lazy<Repository>, private val state: SavedStateHandle) : ViewModel(){
    // it will post updates when there is a change

    private var _beer = MutableLiveData<List<Beer>>()
    val beer : LiveData<List<Beer>> get() = _beer

    init {
        viewModelScope.launch(Dispatchers.Main) {
            state.get<Int>("id")?.let { fetchBeer(repository, _beer, it) }
        }
    }

}

private suspend fun fetchBeer(
    repository: Lazy<Repository>,
    beer: MutableLiveData<List<Beer>>,
    beerId: Int
    )
{
    val response = repository.value.getBeer(beerId)
    Log.d("response", response.toString())
    if (response.isSuccessful && response.body() !=null) {
        Log.d("response", response.message())
        //This line of code case the problem! Type mismatch: inferred type is Beer? but List<Beer>! was expected
        //the response is of type : Response<List<Beer>> so the response.body should be of type List<Beer>
        //in the error it says the inferred type is Beer?
        beer.postValue(response.body())
    } else {
        Log.d("error", response.message())
    }

}

