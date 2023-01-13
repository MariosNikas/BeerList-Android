package com.testproject.beerlist_compose.data

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testproject.beerlist_compose.domain.Beer
import com.testproject.beerlist_compose.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//private val repository : Repository = Repository(AppModule.apiService)
class MainViewModel @Inject constructor(private val repository: Lazy<Repository>) : ViewModel() {
    // it will post updates when there is a change

    private var _beerList = MutableLiveData<List<Beer>>()

    // mutable live data list can only be observed not changed
    val beerList: LiveData<List<Beer>> get() = _beerList

    //call fatchbeer to make the api call using the repository class
    init {
        viewModelScope.launch(Dispatchers.Main) {
            fetchBeers(repository, _beerList, 1)
        }
    }



}

private suspend fun fetchBeers(
    repository: Lazy<Repository>,
    beerList: MutableLiveData<List<Beer>>,
    page: Int
) {
    val response = repository.value.getBeers(page.toString())
    if (response.isSuccessful) {
        Log.d("response", response.body().toString())
        beerList.postValue(response.body())
    } else {
        Log.d("error", response.message())
    }
}