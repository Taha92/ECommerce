package com.example.ecommerce.screen.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.model.OrderHistoryItem
import com.example.ecommerce.model.Product
import com.example.ecommerce.navigation.ShoppingScreens
import com.example.ecommerce.screen.cart.CartScreenViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(topBar = {
        ShoppingAppBar(title = "Profile",
            isMainScreen = false,
            navController = navController
        )
    }) {
        //content
        Surface(modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxSize()
        ) {
            //profile content
            ProfileContent(navController)
        }
    }
}

@Composable
fun ProfileContent(navController: NavController) {

    TextButton(onClick = {
        navController.navigate(ShoppingScreens.OrderHistoryScreen.name)
    }) {
        Text(text = "Order History")
    }
}
