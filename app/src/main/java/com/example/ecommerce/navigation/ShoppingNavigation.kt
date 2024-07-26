package com.example.ecommerce.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.screen.home.HomeScreenViewModel
import com.example.ecommerce.screen.home.ShoppingHomeScreen

@Composable
fun ShoppingNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShoppingScreens.ShoppingHomeScreen.name
    ) {
        composable(ShoppingScreens.ShoppingHomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ShoppingHomeScreen(navController = navController, viewModel = homeViewModel)
        }
    }
}