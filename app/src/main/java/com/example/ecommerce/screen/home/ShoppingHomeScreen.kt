package com.example.ecommerce.screen.home

import android.util.Log
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.ProductXX
import com.example.ecommerce.navigation.ShoppingScreens
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.gson.Gson


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

    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(items = listOfProducts) {product ->
            ProductsGridView(product, navController)
        }
    }
}

@Composable
private fun ProductsGridView(product: ProductXX, navController: NavController) {

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
                    text = "₺${product.price}",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = product.name,
                    maxLines = 3,
                    overflow = TextOverflow.Clip,
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
                    price = product.priceText,
                    image = product.thumbnailURL,
                    quantity = "1",
                )
                performDatabaseOperation(mProduct)
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
                contentDescription = "",
            )
        }
    }
}

fun performDatabaseOperation(product: Product) {

    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("products")
    val query = dbCollection.whereEqualTo("name", product.name.toString())

    query.get().addOnSuccessListener { snapshot ->
        for (document in snapshot) {
            if (document.exists()) {
                //Update quantity
                updateProductInDatabase(dbCollection, document)
            } else {
                //Save new item
                saveProductInDatabase(product, dbCollection)
            }
        }
    }
}

fun saveProductInDatabase(product: Product, dbCollection: CollectionReference) {
    if (product.toString().isNotEmpty()) {
        dbCollection.add(product)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Success", "SaveToFirebase: Saved Successfully!")
                            //navController.popBackStack()
                        }
                    }
                    .addOnFailureListener {
                        Log.d("Error", "SaveToFirebase: Error updating doc")
                    }
            }
    }
}

fun updateProductInDatabase(
    dbCollection: CollectionReference,
    document: QueryDocumentSnapshot
) {
    val increment = (document.data["quantity"] as String).toInt() + 1
    dbCollection.document(document.id)
        .update(hashMapOf("quantity" to increment.toString()) as Map<String, Any>)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Success", "SaveToFirebase: Updated Successfully!")
            }
        }
        .addOnFailureListener {
            Log.d("Error", "SaveToFirebase: Error updating doc")
        }
}
