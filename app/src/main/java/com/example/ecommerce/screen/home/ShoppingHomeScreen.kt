package com.example.ecommerce.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.ecommerce.component.ReaderAppBar

@Composable
fun ShoppingHomeScreen(navController: NavHostController) {
    Scaffold(topBar = {
        ReaderAppBar(title = "Shopping App", navController = navController)
    }) {
        //content
        Surface(modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxSize()
        ) {
            //home content
            //HomeContent(navController, viewModel)
            HomeContent(navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavHostController) {

}
