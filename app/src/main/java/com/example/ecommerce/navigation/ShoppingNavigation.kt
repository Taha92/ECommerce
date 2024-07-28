package com.example.ecommerce.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.screen.ShoppingSplashScreen
import com.example.ecommerce.screen.cart.CartScreenViewModel
import com.example.ecommerce.screen.cart.ShoppingCartScreen
import com.example.ecommerce.screen.home.HomeScreenViewModel
import com.example.ecommerce.screen.home.ShoppingHomeScreen
import com.example.ecommerce.screen.login.ShoppingLoginScreen

@Composable
fun ShoppingNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShoppingScreens.ShoppingSplashScreen.name
    ) {
        composable(ShoppingScreens.ShoppingSplashScreen.name) {
            ShoppingSplashScreen(navController = navController)
        }

        composable(ShoppingScreens.ShoppingHomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ShoppingHomeScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(ShoppingScreens.ShoppingCartScreen.name) {
            val cartViewModel = hiltViewModel<CartScreenViewModel>()
            ShoppingCartScreen(navController = navController, viewModel = cartViewModel)
        }

        composable(ShoppingScreens.LoginScreen.name) {
            ShoppingLoginScreen(navController = navController)
        }
    }
}