package com.testproject.beerlist_compose.UiClasses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    Text(text = "temporary")
}