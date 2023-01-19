package com.testproject.beerlist_compose.UiClasses

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.testproject.beerlist_compose.data.MainViewModel
import com.testproject.beerlist_compose.domain.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.List

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), onclickfun: (Beer) -> Unit
){
    val beers : LazyPagingItems<Beer> = viewModel.flow.collectAsLazyPagingItems()
    var searchBeers = remember{ mutableStateListOf<Beer>() }
    // Fetching the Local Context
    val mContext = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Declaring integer values
    // for year, month and day
    val fromYear: Int
    val fromMonth: Int
    val fromDay: Int

    // Initializing a Calendar
    val fromCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    fromYear = fromCalendar.get(Calendar.YEAR)
    fromMonth = fromCalendar.get(Calendar.MONTH)
    fromDay = fromCalendar.get(Calendar.DAY_OF_MONTH)

    fromCalendar.time = Date()
    val fromDate = remember { mutableStateOf("") }
    val fromDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, fromYear: Int, fromMonth: Int, fromDayOfMonth: Int ->
            fromDate.value = "${fromMonth+1}-$fromYear"
        }, fromYear, fromMonth, fromDay
    )

    // Declaring integer values
    // for year, month and day
    val toYear: Int
    val toMonth: Int
    val toDay: Int

    // Initializing a Calendar
    val toCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    toYear = toCalendar.get(Calendar.YEAR)
    toMonth = toCalendar.get(Calendar.MONTH)
    toDay = toCalendar.get(Calendar.DAY_OF_MONTH)

    toCalendar.time = Date()
    val toDate = remember { mutableStateOf("") }
    val toDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, toYear: Int, toMonth: Int, toDayOfMonth: Int ->
            toDate.value = "${toMonth+1}-$toYear"
        }, toYear, toMonth, toDay
    )
    var searchMode  = remember { mutableStateOf(false) }

    // Declaring a string value to
    // store date in string format

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("The list of Beers from the punkapi") },
                backgroundColor = MaterialTheme.colors.background
            )
        },
    ) { innerPadding ->
        Column() {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Button(onClick = {
                    fromDatePickerDialog.show()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (fromDate.value!="")"from: \n${fromDate.value}" else "date from", color = Color.White)
                }
                Button(onClick = {
                    toDatePickerDialog.show()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)),modifier = Modifier.weight(1f) ) {
                    Text(text = if (toDate.value!="")"to: \n${toDate.value}" else "date to", color = Color.White)
                }

                ExtendedFloatingActionButton(
                    onClick = {
                        coroutineScope.async {
                            if (!searchMode.value) {
                                //DATES ARE NOT PASSED IN THE ONCLICK ????


                                Log.d("dates", "${fromDate.value}   ${toDate.value} ")
                                val beerList: List<Beer> =
                                    viewModel.SearchBeers(fromDate.value, toDate.value)!!
                                for (beer in beerList) {
                                    searchBeers.add(beer)
                                    Log.d("responseSearch2", beer.toString())

                                }

                            } else {
                                searchBeers.clear()
                            }
                            searchMode.value = !searchMode.value
                        }
                    },
                    modifier = Modifier.weight(1f).background(color = Color.Green),
                    icon = {
                        if(searchMode.value)
                        Icon(
                            Icons.Filled.Refresh,
                            contentDescription = "Refresh"
                        )
                        else
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search"
                            )
                    },
                    text = {if (searchMode.value) Text(text = "Refresh") else Text(text = "Search")}
                )
            }
            LazyColumn(contentPadding = innerPadding, modifier = Modifier.fillMaxSize()) {
                if (!searchMode.value){
                items(beers) { beer ->
                    lazyCollumnitem(beer = beer!!, onclickItem ={onclickfun(beer)} )}
                }
                else{
                    items(searchBeers) { beer ->
                        lazyCollumnitem(beer = beer, onclickItem ={onclickfun(beer)} )}
                }
                }
            }
        }
    }








@Composable
fun lazyCollumnitem(beer: Beer, onclickItem: (Beer) -> Unit) {
    Surface(modifier = Modifier.clickable { onclickItem(beer) }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(3.dp, MaterialTheme.colors.primary, RoundedCornerShape(6))
        ) {
            AsyncImage(
                model = beer.image_url,
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
            Column(modifier = Modifier.padding(all = 8.dp)) {
                Text(text = beer.name, style = MaterialTheme.typography.h5)
                Text(text = beer.tagline)
            }
        }
    }
}