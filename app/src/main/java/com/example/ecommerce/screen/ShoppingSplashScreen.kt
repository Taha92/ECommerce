package com.example.ecommerce.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.ecommerce.R
import com.example.ecommerce.navigation.ShoppingScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Preview
@Composable
fun ShoppingSplashScreen(navController: NavController = NavController(LocalContext.current)) {

    LaunchedEffect(key1 = true) {

        delay(2000L)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(ShoppingScreens.LoginScreen.name)
        } else {
            navController.navigate(ShoppingScreens.ShoppingHomeScreen.name)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.mipmap.splash),
            contentDescription = "Splash icon",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}