package com.example.ecommerce.screen.history

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.model.OrderHistoryItem

@Composable
fun OrderHistoryScreen(
    navController: NavController = NavController(LocalContext.current),
    orderHistoryViewModel: OrderHistoryViewModel
) {
    Scaffold(topBar = {
        ShoppingAppBar(
            title = "History",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
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
            //Order history content
            Column(modifier = Modifier
                .padding(10.dp)
            ) {
                ExpandableCard(
                    viewModel = orderHistoryViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    titleFontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    viewModel: OrderHistoryViewModel
) {
    val listOfOrders: List<OrderHistoryItem>

    // Card components to be
    var expandedState by remember {
        mutableStateOf(false)
    }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

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
        /*listOfProducts = viewModel.data.value.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }*/
        listOfOrders = viewModel.data.value.data!!

        LazyColumn {
            items(items = listOfOrders) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                    //colors = CardDefaults.cardColors(containerColor = Color.White),
                    //shape = shape,
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    // card content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .weight(6f)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(2.dp),
                                    text = "Order ID: ${product.orderId}",
                                    fontSize = titleFontSize,
                                    fontWeight = titleFontWeight,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(modifier = Modifier
                                    .padding(2.dp),
                                    text = "Date: ${product.date}",
                                    //fontSize = 14.sp,
                                    fontWeight = titleFontWeight,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(modifier = Modifier
                                    .padding(2.dp),
                                    text = "Total: ₺${product.totalBill}",
                                    //fontSize = 14.sp,
                                    fontWeight = titleFontWeight,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            IconButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .alpha(0.2f)
                                    .rotate(rotationState),
                                onClick = {
                                    expandedState = !expandedState
                                }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Drop-Down Arrow",
                                    tint = Color.Black,
                                )
                            }
                        }

                        if (expandedState) {
                            for (item in product.products!!) {

                                Divider(modifier = Modifier.padding(top = 8.dp))

                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        shape = RoundedCornerShape(9.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                        border = BorderStroke(2.dp, color = Color.LightGray)
                                    ) {
                                        Image(
                                            painter = rememberImagePainter(data = item.image),
                                            contentDescription = "Product image",
                                            modifier = Modifier
                                                .width(96.dp)
                                                .heightIn(100.dp)
                                                .padding(end = 4.dp),
                                        )
                                    }

                                    Column(modifier = Modifier
                                        .padding(start = 16.dp, top = 6.dp)
                                        .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = item.name.toString(),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Quantity: ${item.quantity.toString()}",
                                            style = MaterialTheme.typography.titleMedium,
                                            )
                                        Text(
                                            text = "Price: ${item.priceWithDecimal}",
                                            style = MaterialTheme.typography.titleMedium,
                                            )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No previous orders...")
        }
    }
}
