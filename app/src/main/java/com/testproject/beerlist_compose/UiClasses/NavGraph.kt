package com.testproject.beerlist_compose.UiClasses

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


//navigation graph class containing the logic for the navigation between the 2 screens
@ExperimentalAnimationApi
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
) {
    AnimatedNavHost(navController = navController, startDestination = Screen.Splash.route)
    {
        composable(
            route = Screen.Splash.route,
            exitTransition = { fadeOut(tween(500)) })
        {
            SplashScreen(navController)
        }
        composable(
            route = Screen.Main.route,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
        ) {
            MainScreen(onclickfun = {
                navController.navigate("Details_Screen/" + it.id)
            })
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            DetailsScreen()
        }
    }
}