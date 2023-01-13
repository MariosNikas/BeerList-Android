package com.testproject.beerlist_compose.UiClasses

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.ViewModelInject
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
){
    NavHost(navController = navController, startDestination = Screen.Main.route)
    {
        composable(
            route = Screen.Main.route
        ){
            MainScreen( onclickfun = {
                navController.navigate("Details_Screen/"+it.id)})
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("id"){type = NavType.IntType})
        ){
            val beerID = it.arguments?.getInt("id")
            if (beerID != null) {
                DetailsScreen( id = beerID)
            }
            else
                DetailsScreen(id = 0)
    }
}
}