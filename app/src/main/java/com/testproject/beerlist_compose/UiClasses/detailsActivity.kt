package com.testproject.beerlist_compose.UiClasses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.testproject.beerlist_compose.data.MainViewModel

class detailsActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            DetailsActivityComposable()
        }

    }
}

@Composable
fun DetailsActivityComposable() {
    val context = LocalContext.current
    val intent = (context as detailsActivity).intent
    val name = intent.getStringExtra("name")
    val description = intent.getStringExtra("description")
    val brewed = intent.getStringExtra("brewed")
    val tagline = intent.getStringExtra("tagline")
    val image_url = intent.getStringExtra("image_url")
    Column( horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)){
        AsyncImage(
            model = image_url,
            contentDescription = null,
            modifier = Modifier.padding(16.dp)
        )
        if (name != null) {
            Text(style = MaterialTheme.typography.h4, text = name)
        }
        if (tagline != null) {
            Text(style = MaterialTheme.typography.subtitle1, text = tagline)
        }
        if (description != null) {
            Text(style = MaterialTheme.typography.subtitle2, text = description, modifier = Modifier.padding(16.dp))
        }
        if (brewed != null) {
            Text(style = MaterialTheme.typography.subtitle2, text = "brewed on:$brewed")
        }
    }
}