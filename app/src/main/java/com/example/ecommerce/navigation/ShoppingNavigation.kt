package com.example.ecommerce.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecommerce.model.ProductXX
import com.example.ecommerce.screen.ShoppingSplashScreen
import com.example.ecommerce.screen.card.CardDetailScreen
import com.example.ecommerce.screen.cart.CartScreenViewModel
import com.example.ecommerce.screen.cart.CheckoutScreen
import com.example.ecommerce.screen.cart.ShoppingCartScreen
import com.example.ecommerce.screen.detail.ItemDetailScreen
import com.example.ecommerce.screen.history.OrderHistoryScreen
import com.example.ecommerce.screen.home.HomeScreenViewModel
import com.example.ecommerce.screen.home.ShoppingHomeScreen
import com.example.ecommerce.screen.login.ShoppingLoginScreen
import com.example.ecommerce.screen.otp.OtpScreen
import com.google.gson.Gson

@Composable
fun ShoppingNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShoppingScreens.OtpScreen.name
    ) {
        composable(ShoppingScreens.ShoppingSplashScreen.name) {
            ShoppingSplashScreen(navController = navController)
        }

        composable(ShoppingScreens.ShoppingHomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ShoppingHomeScreen(navController = navController, viewModel = homeViewModel)
        }

        val detailName = ShoppingScreens.ItemDetailScreen.name
        composable(
            "$detailName?selectedProduct={selectedProduct}",
            arguments = listOf(
                navArgument(
                    name = "selectedProduct"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            )
        ) {
            val selectedProductJsonString =  it.arguments?.getString("selectedProduct")
            val selectedProduct = Gson().fromJson(selectedProductJsonString, ProductXX::class.java)
            ItemDetailScreen(navController = navController, product = selectedProduct)
        }

        composable(ShoppingScreens.ShoppingCartScreen.name) {
            val cartViewModel = hiltViewModel<CartScreenViewModel>()
            ShoppingCartScreen(navController = navController, viewModel = cartViewModel)
        }

        composable(ShoppingScreens.LoginScreen.name) {
            ShoppingLoginScreen(navController = navController)
        }

        composable(ShoppingScreens.CardDetailScreen.name) {
            CardDetailScreen(navController = navController)
        }

        composable(ShoppingScreens.OrderHistoryScreen.name) {
            OrderHistoryScreen(navController = navController)
        }

        val checkoutScreenName = ShoppingScreens.CheckoutScreen.name
        composable("$checkoutScreenName/{totalPrice}", arguments = listOf(navArgument("totalPrice") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("totalPrice").let {
                CheckoutScreen(navController = navController, totalPrice = it.toString())
            }
        }

        composable(ShoppingScreens.CheckoutScreen.name) {
            CheckoutScreen(navController = navController, totalPrice = it.toString())
        }

        composable(ShoppingScreens.OtpScreen.name) {
            OtpScreen(navController = navController, totalPrice = it.toString())
        }
    }
}