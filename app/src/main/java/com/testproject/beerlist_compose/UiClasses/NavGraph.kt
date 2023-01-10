package com.testproject.beerlist_compose.UiClasses

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.testproject.beerlist_compose.data.MainViewModel
import com.testproject.beerlist_compose.domain.Beer

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
){
    NavHost(navController = navController, startDestination = Screen.Main.route)
    {
        composable(
            route = Screen.Main.route
        ){
            MainScreen(viewModel, onclickfun = {
                navController.currentBackStackEntry?.savedStateHandle?.set("beer", it)
                navController.navigate(Screen.Details.route)})
        }
        composable(route = Screen.Details.route){
           val beer = navController.previousBackStackEntry?.savedStateHandle?.get<Beer>("beer")
            if (beer != null) {
                DetailsScreen(beer)
            }
            else
                DetailsScreen()
        }
    }
}