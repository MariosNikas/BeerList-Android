package com.testproject.beerlist_compose.UiClasses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.testproject.beerlist_compose.data.DetailsViewModel
import com.testproject.beerlist_compose.data.MainViewModel
import com.testproject.beerlist_compose.ui.theme.BeerListcomposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController : NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            BeerListcomposeTheme {
                navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SetUpNavGraph(navController)
                }
            }
        }
    }
}
