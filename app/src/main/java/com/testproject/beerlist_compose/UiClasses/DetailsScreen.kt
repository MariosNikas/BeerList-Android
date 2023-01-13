package com.testproject.beerlist_compose.UiClasses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.testproject.beerlist_compose.data.DetailsViewModel
import com.testproject.beerlist_compose.data.MainViewModel
import com.testproject.beerlist_compose.domain.Beer
import kotlinx.coroutines.launch


@Composable
fun DetailsScreen(id : Int){
    val viewModel: DetailsViewModel = hiltViewModel()
    val beer  = viewModel.beer.observeAsState()

    if (beer.value!=null) {
        val name = beer.value!!.name
        val description = beer.value!!.description
        val brewed = beer.value!!.first_brewed
        val tagline = beer.value!!.tagline
        val image_url = beer.value!!.image_url
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = image_url,
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                style = MaterialTheme.typography.h4,
                text = name,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                style = MaterialTheme.typography.subtitle1,
                text = tagline,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                style = MaterialTheme.typography.subtitle2,
                text = description,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(style = MaterialTheme.typography.subtitle2, text = "brewed on: $brewed")
        }
    }
    else{
        Text(
            style = MaterialTheme.typography.subtitle2,
            text = "ERROR",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(16.dp)
        )
    }
}