package com.testproject.beerlist_compose.UiClasses

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


//navigation graph class containing the logic for the navigation between the 2 screens
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Screen.Main.route)
    {
        composable(
            route = Screen.Main.route
        ) {
            MainScreen(onclickfun = {
                navController.navigate("Details_Screen/" + it.id)
            })
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            DetailsScreen()
        }
    }
}