package com.testproject.beerlist_compose.UiClasses

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.testproject.beerlist_compose.data.DetailsViewModel
import com.testproject.beerlist_compose.data.MainViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    viewModelMain: MainViewModel,
    viewmodelDetails : DetailsViewModel
){
    NavHost(navController = navController, startDestination = Screen.Main.route)
    {
        composable(
            route = Screen.Main.route
        ){
            MainScreen(viewModelMain, onclickfun = {
                navController.currentBackStackEntry?.savedStateHandle?.set("beerID", it.id.toInt())
                navController.navigate(Screen.Details.route)})
        }
        composable(route = Screen.Details.route){
            val beerID = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("beerID")
            if (beerID!=null) {
                DetailsScreen()
            }
            else{
                DetailsScreen()
            }
    }
}
}