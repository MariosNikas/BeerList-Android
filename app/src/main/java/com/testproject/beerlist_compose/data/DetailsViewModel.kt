package com.testproject.beerlist_compose.data

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.testproject.beerlist_compose.domain.Beer
import com.testproject.beerlist_compose.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor (private val repository: Lazy<Repository>, private val state: SavedStateHandle) : ViewModel(){
    // it will post updates when there is a change

    private var _beer = MutableLiveData<Beer>()
    val beer : LiveData<Beer> get() = _beer

    init {
        viewModelScope.launch(Dispatchers.Main) {
            state.get<Int>("id")?.let { fetchBeer(repository, _beer, it) }
        }
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
        Log.d("response", response.message())
        beer.postValue(response.body())
    } else {
        Log.d("error", response.message())
    }

}

