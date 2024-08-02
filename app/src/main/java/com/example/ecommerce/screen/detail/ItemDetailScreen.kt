package com.example.ecommerce.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.ecommerce.component.RoundedSubmitButton
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.component.performDatabaseOperation
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.ProductXX
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ItemDetailScreen(
    navController: NavController = NavController(LocalContext.current),
    product: ProductXX,
) {
    Scaffold(topBar = {
        ShoppingAppBar(
            title = "Product Detail",
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
            .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
            .fillMaxSize()
        ) {
            //item content
            ItemDetailContent(product)
        }
    }
}

@Composable
fun ItemDetailContent(product: ProductXX) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        val context = LocalContext.current

        Box(modifier = Modifier.weight(0.8f)) {
            Card(
                shape = RoundedCornerShape(9.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(406.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberImagePainter(data = product.thumbnailURL),
                        contentDescription = "Product image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(end = 4.dp)
                            .weight(0.7f),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = "₺${product.price}",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                    Text(
                        text = product.name,
                        maxLines = 3,
                        overflow = TextOverflow.Clip,
                        textAlign = TextAlign.Center,
                        fontSize = 26.sp,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(0.3f)
                    )
                }
            }
        }

        Box(modifier = Modifier.weight(0.1f)) {
            RoundedSubmitButton(label = "Add to Cart") {
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
            }
        }
    }
}
