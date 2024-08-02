package com.example.ecommerce.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.component.performDatabaseOperation
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.ProductXX
import com.example.ecommerce.navigation.ShoppingScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.delay


@Composable
fun ShoppingHomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ShoppingAppBar(
            title = "Shopping App",
            navController = navController,
            viewModel = viewModel)
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
    navController: NavController,
    viewModel: HomeScreenViewModel
) {
    val listOfProducts = viewModel.list

    if (viewModel.isLoading) {
        Column(modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Text(text = "Loading...")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(items = listOfProducts) {product ->
                ProductsGridView(product, navController)
            }
        }
    }
}

@Composable
private fun ProductsGridView(product: ProductXX, navController: NavController) {

    val context = LocalContext.current
    val iconSize = 24.dp
    val offsetInPx = LocalDensity.current.run { (iconSize / 2).roundToPx() }

    Box(modifier = Modifier.padding((iconSize / 2))) {
        Card(
            modifier = Modifier
                .height(152.dp)
                .clickable {
                    val selectedProductJson = Gson().toJson(product)
                    navController.navigate(ShoppingScreens.ItemDetailScreen.name + "?selectedProduct=${selectedProductJson}")
                },
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Image(
                    painter = rememberImagePainter(data = product.thumbnailURL),
                    contentDescription = "Product image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(end = 4.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = String.format("₺%.2f", product.price),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = product.name,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(4.dp)
                )
            }

        }

        IconButton(
            onClick = {
                //Save product to firestore
                val mProduct = Product(
                    name = product.name,
                    shortDescription = product.shortDescription,
                    price = product.priceText,
                    priceWithDecimal = product.price,
                    image = product.thumbnailURL,
                    quantity = "1",
                    userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                )
                performDatabaseOperation(mProduct, "Add", context)
            },
            modifier = Modifier
                .offset {
                    IntOffset(x = +offsetInPx, y = -offsetInPx)
                }
                .clip(CircleShape)
                .background(White)
                .size(32.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add icon",
            )
        }
    }
}
