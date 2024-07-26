package com.example.ecommerce.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.ecommerce.component.ReaderAppBar

@Composable
fun ShoppingHomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
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
            HomeContent(navController, viewModel)
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeScreenViewModel
) {
    val numbers = (0..20).toList()

    LazyVerticalGrid(
        columns = GridCells.Fixed(4)
    ) {
        items(numbers.size) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Number")
                Text(text = "  $it",)
            }
        }
    }
}
