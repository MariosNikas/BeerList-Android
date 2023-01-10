package com.testproject.beerlist_compose.UiClasses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.testproject.beerlist_compose.domain.Beer

@Composable
fun DetailsScreen(beer: Beer = Beer("1", "name", "tagline", "image", "01/01/2001", "description")){

    val name = beer.name
    val description = beer.description
    val brewed = beer.first_brewed
    val tagline = beer.tagline
    val image_url = beer.image_url
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
        if (name != null) {
            Text(
                style = MaterialTheme.typography.h4,
                text = name,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        if (tagline != null) {
            Text(style = MaterialTheme.typography.subtitle1, text = tagline)
        }
        if (description != null) {
            Text(
                style = MaterialTheme.typography.subtitle2,
                text = description,
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        if (brewed != null) {
            Text(style = MaterialTheme.typography.subtitle2, text = "brewed on: $brewed")
        }
    }
}