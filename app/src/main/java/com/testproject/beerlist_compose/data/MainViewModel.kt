package com.testproject.beerlist_compose.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.*
import com.testproject.beerlist_compose.UiClasses.BeerSource
import com.testproject.beerlist_compose.domain.Beer
import com.testproject.beerlist_compose.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject


@HiltViewModel

class MainViewModel @Inject constructor(private  val apiService: ApiService, private val repository: Repository) : ViewModel() {
    var fromYear: Int
    var fromDay:Int
    var fromMonth:Int
    var toYear: Int
    var toDay:Int
    var toMonth:Int
    var toDate: MutableState<String?> = mutableStateOf(null)
    var fromDate: MutableState<String?> = mutableStateOf(null)
    val fromCalendar = Calendar.getInstance()
    val toCalendar = Calendar.getInstance()
    var searchMode: MutableState<Boolean> = mutableStateOf(false)


    var flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 25)
    ) {
        BeerSource(apiService = apiService, this)
    }.flow.cachedIn(viewModelScope)

    init {
        //fromCalendar.time = Date()
        //toCalendar.time = Date()
        toYear = toCalendar.get(Calendar.YEAR)
        toMonth = toCalendar.get(Calendar.MONTH)
        toDay = toCalendar.get(Calendar.DAY_OF_MONTH)
        fromYear = fromCalendar.get(Calendar.YEAR)
        fromDay= fromCalendar.get(Calendar.DAY_OF_MONTH)
        fromMonth= fromCalendar.get(Calendar.MONTH)
    }

    fun onSearch(){
        if ((toDate.value!=null||fromDate.value!=null)) {
            if (!searchMode.value) {
                //var beerList: List<Beer>
                searchMode.value = !searchMode.value
            }
        } else if (searchMode.value){
            searchMode.value= !searchMode.value
        }
        updatePager()
        //toDate.value=null
        //fromDate.value = null
        // when these 2 lines are commented it works
    }

    fun updatePager() {
        flow = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 25)
        ) {
            BeerSource(apiService = apiService, this)
        }.flow.cachedIn(viewModelScope)
    }






//    suspend fun SearchBeers(
//        dateFrom: String? =null,
//        dateTo: String? =null
//    ) : List<Beer>? {
//        val response = repository.SearchBeers(dateFrom,dateTo)
//        if (response.isSuccessful) {
//            Log.d("response search", response.body().toString())
//        } else {
//            Log.d("error", response.message())
//        }
//        return response.body()
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
