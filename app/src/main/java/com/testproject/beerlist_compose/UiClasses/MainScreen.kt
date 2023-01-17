package com.testproject.beerlist_compose.UiClasses

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.testproject.beerlist_compose.data.MainViewModel
import com.testproject.beerlist_compose.domain.Beer
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), onclickfun: (Beer) -> Unit
){
    val beers : LazyPagingItems<Beer> = viewModel.flow.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("The list of Beers from the punkapi") },
                backgroundColor = MaterialTheme.colors.background
            )
        },
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding, modifier = Modifier.fillMaxSize()) {
                items(beers) { beer ->
                    lazyCollumnitem(beer = beer!!, onclickItem ={onclickfun(beer)} )}
                    }}
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