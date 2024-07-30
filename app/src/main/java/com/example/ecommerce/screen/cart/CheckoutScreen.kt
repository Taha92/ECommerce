package com.example.ecommerce.screen.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerce.R
import com.example.ecommerce.component.RoundedButton
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.navigation.ShoppingScreens

@Composable
fun CheckoutScreen(navController: NavController, totalPrice: String) {
    Scaffold(topBar = {
        ShoppingAppBar(title = "Checkout",
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
            //checkout content
            CheckoutContent(navController, totalPrice)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CheckoutContent(
    navController: NavController = NavController(LocalContext.current),
    totalPrice: String
) {
    val totalPayable = (totalPrice.toDouble() * 10) / 100

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(modifier = Modifier.weight(0.8f)) {
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
                    .height(70.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier
                    .weight(0.3f),
                    shape = CircleShape,
                    color = Color.White,
                    border = BorderStroke(2.dp, color = Color.LightGray)
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.visa_card),
                        contentDescription = "Visa card icon",
                    )
                }

                Column(modifier = Modifier
                    .weight(0.7f)
                    .padding(start = 6.dp)
                ) {
                    Text(
                        text = "Muhammad Taha Imtiaz",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 4.dp))
                    Text(text = "123456********90")
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate(ShoppingScreens.CardDetailScreen.name)
                    }
                ) {
                    Text(text = "Change card")
                }
            }

            Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp))

            Row(modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(0.9f)
                )
                Text(
                    text = "₺${totalPrice}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp))

            Row(modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Home Delivery",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(0.9f)
                )
                Text(
                    text = "Free",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp))

            Row(modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Discount",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(0.9f)
                )
                Text(
                    text = "10%",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp))

            Row(modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Total Payable",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(0.9f),
                    color = Color.Red.copy(alpha = 0.5f)
                )
                Text(
                    text = String.format("₺%.2f", totalPrice.toDouble() - totalPayable),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Red.copy(alpha = 0.5f)
                )
            }
        }

        Box(modifier = Modifier.weight(0.1f)) {
            RoundedButton(label = "Order and Pay") {
                navController.navigate(ShoppingScreens.CardDetailScreen.name)
            }
        }
    }
}
