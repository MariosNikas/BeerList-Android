package com.testproject.beerlist_compose.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.*
import com.testproject.beerlist_compose.UiClasses.BeerSource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel

class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    var fromYear: Int
    var fromDay: Int
    var fromMonth: Int
    var toYear: Int
    var toDay: Int
    var toMonth: Int
    var toDate: MutableState<String?> = mutableStateOf(null)
    var fromDate: MutableState<String?> = mutableStateOf(null)
    private val fromCalendar: Calendar = Calendar.getInstance()
    private val toCalendar = Calendar.getInstance()
    var searchMode: MutableState<Boolean> = mutableStateOf(false)


    var flow = Pager(
        PagingConfig(pageSize = 25)
    ) {
        BeerSource(
            apiService = apiService,
            this.fromDate.value,
            this.toDate.value,
            this.searchMode.value
        )
    }.flow.cachedIn(viewModelScope)

    init {
        toYear = toCalendar.get(Calendar.YEAR)
        toMonth = toCalendar.get(Calendar.MONTH)
        toDay = toCalendar.get(Calendar.DAY_OF_MONTH)
        fromYear = fromCalendar.get(Calendar.YEAR)
        fromDay = fromCalendar.get(Calendar.DAY_OF_MONTH)
        fromMonth = fromCalendar.get(Calendar.MONTH)
    }

    fun onSearch() {
        if ((toDate.value != null || fromDate.value != null)) {
            if (!searchMode.value) {
                searchMode.value = !searchMode.value
            } else {
                //this is ugly but it works
                searchMode.value = !searchMode.value
                searchMode.value = !searchMode.value
            }
        } else if (searchMode.value) {
            searchMode.value = !searchMode.value
        }
        updatePager()
    }

    private fun updatePager() {
        flow = Pager(
            PagingConfig(pageSize = 25)
        ) {
            BeerSource(
                apiService = apiService,
                this.fromDate.value,
                this.toDate.value,
                this.searchMode.value
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun resetDates() {
        fromDate.value = null
        toDate.value = null
    }
}