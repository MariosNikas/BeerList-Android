package com.testproject.beerlist_compose.UiClasses

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.testproject.beerlist_compose.data.MainViewModel
import com.testproject.beerlist_compose.domain.Beer
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onclickfun: (Beer) -> Unit
) {
    val mContext = LocalContext.current
    val fromDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, fromYear: Int, fromMonth: Int, _: Int ->
            viewModel.fromDate.value = "${fromMonth + 1}-$fromYear"
        }, viewModel.fromYear, viewModel.fromMonth, viewModel.fromDay
    )
    val toDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, toYear: Int, toMonth: Int, _: Int ->
            viewModel.toDate.value = "${toMonth + 1}-$toYear"
        }, viewModel.toYear, viewModel.toMonth, viewModel.toDay
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("The list of Beers from the punkapi ") },
                backgroundColor = MaterialTheme.colors.background
            )
        },
    ) { innerPadding ->
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Button(
                    onClick = {
                        fromDatePickerDialog.show()
                    }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)),
                    modifier = Modifier.weight(0.8f)
                ) {
                    Text(
                        text = if (viewModel.fromDate.value != null) "from: \n${viewModel.fromDate.value}" else "date from",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                Button(
                    onClick = {
                        toDatePickerDialog.show()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)),
                    modifier = Modifier.weight(0.8f)
                ) {
                    Text(
                        text = if (viewModel.toDate.value != null) "to: \n${viewModel.toDate.value}" else "date to",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                Button(
                    onClick = { viewModel.resetDates() },
                    modifier = Modifier.weight(0.5f),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "reset dates",
                        tint = Color(0xFFFFFFFF)
                    )
                }
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.onSearch()
                        }

                    },
                    modifier = Modifier
                        .weight(1f),
                    icon = {
                        if ((viewModel.toDate.value == null && viewModel.fromDate.value == null) && viewModel.searchMode.value)
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
                    text = {
                        if (viewModel.searchMode.value && (viewModel.toDate.value == null && viewModel.fromDate.value == null)) Text(
                            text = "Refresh",
                            fontSize = 12.sp
                        ) else Text(
                            text = "Search",
                            fontSize = 12.sp
                        )
                    }
                )
            }
            // without the if it doesnt recompose with the searched list when the search is done
            if (viewModel.searchMode.value) {
                val beers = viewModel.flow.collectAsLazyPagingItems()
                LazyCollumn(beers, paddingValues = innerPadding, onclickfun = onclickfun)

            } else {
                val beers = viewModel.flow.collectAsLazyPagingItems()
                LazyCollumn(beers, paddingValues = innerPadding, onclickfun = onclickfun)
            }
        }
    }
}

@Composable
fun LazyCollumn(
    beers: LazyPagingItems<Beer>,
    paddingValues: PaddingValues,
    onclickfun: (Beer) -> Unit
) {
    LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize()) {
        items(beers) { beer ->
            LazyCollumnitem(beer = beer!!, onclickItem = { onclickfun(beer) })
        }
    }
}

@Composable
fun LazyCollumnitem(beer: Beer, onclickItem: (Beer) -> Unit) {
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