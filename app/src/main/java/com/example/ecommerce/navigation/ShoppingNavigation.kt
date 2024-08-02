package com.example.ecommerce.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecommerce.model.CardInfo
import com.example.ecommerce.model.ProductXX
import com.example.ecommerce.screen.ShoppingSplashScreen
import com.example.ecommerce.screen.card.CardDetailScreen
import com.example.ecommerce.screen.cart.CartScreenViewModel
import com.example.ecommerce.screen.cart.CheckoutScreen
import com.example.ecommerce.screen.cart.ShoppingCartScreen
import com.example.ecommerce.screen.detail.ItemDetailScreen
import com.example.ecommerce.screen.history.OrderHistoryScreen
import com.example.ecommerce.screen.history.OrderHistoryViewModel
import com.example.ecommerce.screen.home.HomeScreenViewModel
import com.example.ecommerce.screen.home.ShoppingHomeScreen
import com.example.ecommerce.screen.login.ShoppingLoginScreen
import com.example.ecommerce.screen.orderSuccess.OrderPlacedScreen
import com.example.ecommerce.screen.otp.OtpScreen
import com.example.ecommerce.screen.profile.ProfileScreen
import com.google.gson.Gson

@Composable
fun ShoppingNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShoppingScreens.OrderPlacedScreen.name
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

        val cardDetailScreenName = ShoppingScreens.CardDetailScreen.name
        composable("$cardDetailScreenName/{totalPrice}", arguments = listOf(navArgument("totalPrice") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("totalPrice").let {
                CardDetailScreen(navController = navController, totalPrice = it.toString())
            }
        }

        composable(ShoppingScreens.OrderHistoryScreen.name) {
            val orderHistoryViewModel = hiltViewModel<OrderHistoryViewModel>()
            OrderHistoryScreen(navController = navController, orderHistoryViewModel)
        }

        val checkoutScreenName = ShoppingScreens.CheckoutScreen.name
        composable(
            "$checkoutScreenName?cardInfo={cardInfo}?totalBill={totalBill}",
            arguments = listOf(
                navArgument(
                    name = "cardInfo"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "totalBill"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            )
        ) {
            val cardInfoJsonString =  it.arguments?.getString("cardInfo")
            val totalBillJsonString =  it.arguments?.getString("totalBill")
            val cardInfo = Gson().fromJson(cardInfoJsonString, CardInfo::class.java)
            CheckoutScreen(navController = navController, cardInfo = cardInfo, totalBill = totalBillJsonString.toString())
        }


        val otpScreenName = ShoppingScreens.OtpScreen.name
        composable("$otpScreenName/{totalBill}", arguments = listOf(navArgument("totalBill") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("totalBill").let {
                val cartViewModel = hiltViewModel<CartScreenViewModel>()
                OtpScreen(navController = navController, totalBill = it.toString(), cartViewModel)
            }
        }

        composable(ShoppingScreens.OrderPlacedScreen.name) {
            OrderPlacedScreen(navController = navController)
        }

        composable(ShoppingScreens.ProfileScreen.name) {
            ProfileScreen(navController = navController)
        }
    }
}