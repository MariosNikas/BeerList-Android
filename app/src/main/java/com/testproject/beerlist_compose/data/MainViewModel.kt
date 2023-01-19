package com.testproject.beerlist_compose.data

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.testproject.beerlist_compose.UiClasses.BeerSource
import com.testproject.beerlist_compose.domain.Beer
import com.testproject.beerlist_compose.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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


    suspend fun SearchBeers(
        dateFrom : String,
        dateTo : String
    ) : List<Beer>? {
        val response = repository.SearchBeers(dateFrom,dateTo)
        if (response.isSuccessful) {
            Log.d("response search", response.body().toString())
        } else {
            Log.d("error", response.message())
        }
        return response.body()
    }
    init {
        viewModelScope.launch (Dispatchers.Main ) {
            Log.d("testing viewmodel", SearchBeers("10-2000","10-2022").toString())
        }
    }


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
