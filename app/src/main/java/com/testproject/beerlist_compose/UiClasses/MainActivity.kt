@file:OptIn(ExperimentalAnimationApi::class)

package com.testproject.beerlist_compose.UiClasses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.testproject.beerlist_compose.ui.theme.BeerListcomposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeerListcomposeTheme {
                navController = rememberAnimatedNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SetUpNavGraph(navController)
                }
            }
        }
    }
}
