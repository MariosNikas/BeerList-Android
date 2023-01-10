package com.testproject.beerlist_compose.UiClasses

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.testproject.beerlist_compose.data.MainViewModel
import com.testproject.beerlist_compose.domain.Beer

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController){
    val beersState = viewModel.beerList.observeAsState()
    val beers = beersState.value
    lazyColllumn(beers, navController)
}


@Composable
fun lazyColllumn(beers: List<Beer>?, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("The list of Beers from the punkapi") },
                backgroundColor = MaterialTheme.colors.background
            )
        },
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding, modifier = Modifier.fillMaxSize()) {
            if (beers != null) {
                items(beers) { beer ->
                    lazyCollumnitem(beer = beer) {
                        navController.navigate(route = Screen.Details.passBeer(beer))
                    }
                }
            }
        }

    }
}

@Composable
fun lazyCollumnitem(beer: Beer, onclickfun: (Beer) -> Unit) {
    Surface(modifier = Modifier.clickable { onclickfun(beer) }) {
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