package com.example.ecommerce.navigation

enum class ShoppingScreens {
    ShoppingSplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ShoppingHomeScreen,
    ItemDetailScreen,
    ShoppingCartScreen,
    CardDetailScreen,
    OtpScreen,
    OrderPlacedScreen,
    OrderHistoryScreen,
    CheckoutScreen,
    ProfileScreen;

    companion object {
        fun fromRoute(route: String?): ShoppingScreens
                = when(route?.substringBefore("/")) {
            ShoppingSplashScreen.name -> ShoppingSplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ShoppingHomeScreen.name -> ShoppingHomeScreen
            ItemDetailScreen.name -> ItemDetailScreen
            ShoppingCartScreen.name -> ShoppingCartScreen
            CardDetailScreen.name -> CardDetailScreen
            OtpScreen.name -> OtpScreen
            OrderPlacedScreen.name -> OrderPlacedScreen
            OrderHistoryScreen.name -> OrderHistoryScreen
            CheckoutScreen.name -> CheckoutScreen
            ProfileScreen.name -> ProfileScreen
            null -> ShoppingHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}