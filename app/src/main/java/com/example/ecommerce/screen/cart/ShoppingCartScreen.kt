package com.example.ecommerce.screen.cart

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.ecommerce.R
import com.example.ecommerce.component.RoundedSubmitButton
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.component.deleteProductFromDatabase
import com.example.ecommerce.component.performDatabaseOperation
import com.example.ecommerce.model.Product
import com.example.ecommerce.navigation.ShoppingScreens
import com.google.gson.Gson

@Composable
fun ShoppingCartScreen(
    navController: NavController,
    viewModel: CartScreenViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        ShoppingAppBar(title = "Cart",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            isMainScreen = false,
            navController = navController,
            viewModel = viewModel
        ) {
            navController.popBackStack()
        }
    }) {
        //content
        Surface(modifier = Modifier
            .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
            .fillMaxSize()
        ) {
            //cart content
            CartContent(navController, viewModel)
        }
    }

}

@Composable
fun CartContent(navController: NavController, viewModel: CartScreenViewModel) {
    var listOfProducts: List<Product>
    var totalPrice by remember { mutableStateOf(0.0) }
    val deletedItem = remember { mutableStateListOf<Product>() }

    if (viewModel.data.value.loading!!) {
        Column(modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Text(text = "Loading...")
        }
    } else if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfProducts = viewModel.data.value.data!!.toMutableList()

        totalPrice = listOfProducts.filter { !deletedItem.contains(it) }
            .sumOf { it.priceWithDecimal!!.toDouble() * it.quantity!!.toDouble() }

        Column {
            Box(modifier = Modifier.weight(0.8f)) {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(
                        items = listOfProducts,
                        itemContent = {product ->
                            androidx.compose.animation.AnimatedVisibility(
                                visible = !deletedItem.contains(product),
                                enter = expandVertically(),
                                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                            ) {
                                ProductRow(
                                    product,
                                    deletedItem,
                                    navController
                                ) { updatedProducts ->
                                    listOfProducts = updatedProducts
                                    totalPrice = updatedProducts.filter { !deletedItem.contains(it) }
                                        .sumOf { it.priceWithDecimal!!.toDouble() * it.quantity!!.toDouble() }
                                }
                            }
                        }
                    )
                }
            }

            if (listOfProducts.any { !deletedItem.contains(it) }) {
                Box(modifier = Modifier.weight(0.1f)) {
                    RoundedSubmitButton {
                        navController.navigate(ShoppingScreens.CardDetailScreen.name + "/${totalPrice}")
                    }
                }
            } else {
                NoItemInCart()
            }
        }

    } else {
        NoItemInCart()
    }
}

@Composable
fun NoItemInCart() {
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No item in cart...")
    }
}

@Composable
fun ProductRow(
    product: Product,
    deletedItem: SnapshotStateList<Product>,
    navController: NavController,
    onProductListUpdated: (List<Product>) -> Unit
) {
    val context = LocalContext.current
    val productQuantity = rememberSaveable { mutableStateOf(product.quantity!!.toInt()) }

    Card(
        shape = RoundedCornerShape(29.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(140.dp)
            /*.clickable {
                val selectedProductJson = Gson().toJson(product)
                navController.navigate(ShoppingScreens.ItemDetailScreen.name + "?selectedProduct=${selectedProductJson}")
            }*/
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = "${product.image}"),
                contentDescription = "Product image",
                modifier = Modifier
                    .width(96.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${product.name}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Text(
                    text = if (product.shortDescription == null) "Description" else "${product.shortDescription}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Text(
                    text = "₺${product.priceWithDecimal}",
                    modifier = Modifier
                        .padding(start = 16.dp)
                )


                Row(modifier = Modifier
                    .padding(4.dp)
                    .height(200.dp)
                    .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            productQuantity.value--
                            if (productQuantity.value == 0) {
                                deleteProductFromDatabase(product)
                                deletedItem.add(product)
                            } else {
                                performDatabaseOperation(product, "Subtract", context)
                            }
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_remove),
                            contentDescription = "Remove icon",
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    Text(
                        text = productQuantity.value.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )

                    IconButton(
                        onClick = {
                            productQuantity.value++
                            performDatabaseOperation(product, "Add", context)
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "Add icon",
                            modifier = Modifier.size(34.dp)
                        )
                    }
                }
            }
        }
    }


}
