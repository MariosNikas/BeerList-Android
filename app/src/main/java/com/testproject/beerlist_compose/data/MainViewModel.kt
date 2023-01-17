package com.testproject.beerlist_compose.data

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.testproject.beerlist_compose.UiClasses.BeerSource
import com.testproject.beerlist_compose.domain.Beer
import com.testproject.beerlist_compose.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(private  val apiService: ApiService, private val repository: Repository) : ViewModel() {
    // it will post updates when there is a change


    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 25)
    ) {
        BeerSource(apiService = apiService)
    }.flow
        .cachedIn(viewModelScope)
//    init {
//        viewModelScope.launch (Dispatchers.Main ) {
//            repository.getBeers()
//        }
//    }


}

//    //call fatchbeer to make the api call using the repository class
//    init {
//        viewModelScope.launch(Dispatchers.Main) {
//            val Beer: Flow<PagingData<Beer>> = Pager(PagingConfig(pageSize = 25)) {
//                BeerSource()
//            }.flow.cachedIn(viewModelScope)
//            fetchBeers(repository, _beerList, 1)
//        }
//    }
//
//
//
//}
//
//private suspend fun fetchBeers(
//    repository: Lazy<Repository>,
//    beerList: MutableLiveData<List<Beer>>,
//    page: Int
//) {
//    val response = repository.value.getBeers(page.toString())
//    if (response.isSuccessful) {
//        Log.d("response", response.body().toString())
//        beerList.postValue(response.body())
//    } else {
//        Log.d("error", response.message())
//    }
//}