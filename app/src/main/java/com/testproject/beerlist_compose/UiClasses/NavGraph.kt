package com.testproject.beerlist_compose.UiClasses

import android.provider.ContactsContract.Profile
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
            MainScreen(viewModel, navController)
        }
        composable(Screen.Details.route
//            arguments = listOf (
//                navArgument(DETAIL_ID){
//                    type = NavType.StringType
//                },
//                navArgument(DETAIL_NAME){
//                    type = NavType.StringType
//                },
//                navArgument(DETAIL_TAGLINE){
//                    type= NavType.StringType
//                },
//                navArgument(DETAIL_DESCRIPTION){
//                    type= NavType.StringType
//                },
//                navArgument(DETAIL_BREWED){
//                    type= NavType.StringType
//                },
//                navArgument(DETAIL_IMAGE){
//                    type= NavType.StringType
//                }

        ){
            val id = it.arguments?.getString("id")
            val name = it.arguments?.getString("name")
            val tagline = it.arguments?.getString("tagline")
            val description = it.arguments?.getString("description")
            val brewed = it.arguments?.getString("brewed")
            val image = it.arguments?.getString("image")

            val beer = Beer(id!!,name!!,tagline!!,description!!,brewed!!,image!!)
            DetailsScreen(beer)
        }
    }
}