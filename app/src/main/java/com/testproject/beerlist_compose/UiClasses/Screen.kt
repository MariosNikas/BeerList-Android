package com.testproject.beerlist_compose.UiClasses

sealed class Screen (val route :String){
    object Main: Screen(route = "Main_Screen")
    object Details: Screen(route = "Details_Screen")

}
