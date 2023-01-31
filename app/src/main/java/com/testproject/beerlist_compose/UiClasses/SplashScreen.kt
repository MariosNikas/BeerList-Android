package com.testproject.beerlist_compose.UiClasses

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(1.5f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 3f,
            animationSpec = tween(
                durationMillis = 800,
                easing = { OvershootInterpolator(2f).getInterpolation(it) })
        )
        delay(500)
        navController.navigate(Screen.Main.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .fillMaxSize()
        .padding(50.dp)) {
        Text(text = "BEER APP", color = Color.Black, modifier = Modifier.scale(scale.value))
    }
}