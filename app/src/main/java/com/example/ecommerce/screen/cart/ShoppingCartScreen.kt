package com.example.ecommerce.screen.cart

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.ecommerce.R
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.component.RoundedButton
import com.example.ecommerce.model.Product

@Composable
fun ShoppingCartScreen(
    navController: NavController,
    viewModel: CartScreenViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        ShoppingAppBar(title = "Cart",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        //content
        Surface(modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxSize()
        ) {
            //cart content
            CartContent(navController, viewModel)
        }
    }

}

@Composable
fun CartContent(navController: NavController, viewModel: CartScreenViewModel) {
    var listOfProducts= emptyList<Product>()
    //val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        /*listOfProducts = viewModel.data.value.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }*/
        listOfProducts = viewModel.data.value.data!!.toList()

        Column {
            Box(modifier = Modifier.weight(0.9f)) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(items = listOfProducts) { product ->
                        ProductRow(product)
                    }
                }
            }

            Box(modifier = Modifier.weight(0.1f)) {
                RoundedButton()
            }
        }
    }
}

@Composable
fun ProductRow(product: Product) {

    Card(
        shape = RoundedCornerShape(29.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(136.dp)
            .clickable { }
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
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Text(
                    text = "Description",
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Text(
                    text = "${product.price}",
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
                        text = "${product.quantity}",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )

                    IconButton(
                        onClick = {

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
