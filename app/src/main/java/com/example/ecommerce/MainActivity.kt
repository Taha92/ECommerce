package com.example.ecommerce

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerce.ui.theme.ECommerceTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceTheme {
                val db = FirebaseFirestore.getInstance()
                val product: MutableMap<String, Any> = HashMap()
                product["name"] = "Lipton Ice Tea Peach"
                product["price"] = "83.3"
                product["image"] = "https://market-product-images-cdn.getirapi.com/product/2f9dcc14-8eaa-4822-93f8-4d1fa259a8fb.jpg"


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    db.collection("products")
                        .add(product)
                        .addOnSuccessListener {
                            Log.d("DB", "onCreate: ${it.id}")
                        }.addOnFailureListener {
                            Log.d("DB", "onCreate: ${it}")
                        }
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ECommerceTheme {
        Greeting("Android")
    }
}