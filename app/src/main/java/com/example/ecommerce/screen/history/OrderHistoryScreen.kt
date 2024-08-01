package com.example.ecommerce.screen.history

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.ecommerce.component.ShoppingAppBar

@Preview
@Composable
fun OrderHistoryScreen(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(topBar = {
        ShoppingAppBar(
            title = "History",
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
            //Order history content
            Column(modifier = Modifier
                .padding(10.dp)) {
                ExpandableCard(
                    title = "Order Id",
                    description = "This is expandable card"
                )
            }
        }
    }
}

@Preview
@Composable
fun OrderHistoryContent() {
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
                painter = rememberImagePainter(data = ""),
                contentDescription = "Product image",
                modifier = Modifier
                    .width(96.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "asdas",
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Text(
                    text = "Description",
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Text(
                    text = "sadfgg",
                    modifier = Modifier
                        .padding(start = 16.dp)
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.titleSmall.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    //shape: Shape = sharpShapes.medium,
    padding: Dp = 12.dp
) {
    // Card components to be
    var expandedState by remember {
        mutableStateOf(false)
    }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
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
                    .weight(6f)) {
                    Text(
                        modifier = Modifier
                            .padding(2.dp),
                        text = title,
                        fontSize = titleFontSize,
                        fontWeight = titleFontWeight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(modifier = Modifier
                        .padding(2.dp),
                        text = "16/06/2024",
                        fontSize = 14.sp,
                        fontWeight = titleFontWeight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(modifier = Modifier
                        .padding(2.dp),
                        text = "₺51.20",
                        fontSize = 14.sp,
                        fontWeight = titleFontWeight,
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
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = "https://market-product-images-cdn.getirapi.com/product/62a59d8a-4dc4-4b4d-8435-643b1167f636.jpg"),
                        contentDescription = "Product image",
                        modifier = Modifier
                            .width(96.dp)
                            .padding(end = 4.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Item Name",
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                        Text(
                            text = "Quantity",
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                        Text(
                            text = "Total Price",
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )

                    }
                }
                /*Text(
                    text = description,
                    fontSize = descriptionFontSize,
                    fontWeight = descriptionFontWeight,
                    maxLines = descriptionMaxLines,
                    overflow = TextOverflow.Ellipsis
                )*/
            }
        }
    }
}
