package com.example.ecommerce.screen.otp

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.ecommerce.component.OtpTextField
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.component.deleteProductFromDatabase
import com.example.ecommerce.model.OrderHistoryItem
import com.example.ecommerce.model.Product
import com.example.ecommerce.navigation.ShoppingScreens
import com.example.ecommerce.screen.cart.CartScreenViewModel
import com.example.paymentsdk.Payment
import com.example.paymentsdk.PaymentCallback
import com.example.paymentsdk.PaymentInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun OtpScreen(navController: NavHostController, totalBill: String, cartViewModel: CartScreenViewModel) {
    Scaffold(topBar = {
        ShoppingAppBar(title = "Payment",
            isMainScreen = false,
            navController = navController
        )
    }) {
        //content
        Surface(modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxSize()
        ) {
            //payment content
            PaymentContent(navController, totalBill, cartViewModel)
        }
    }
}

@Composable
fun PaymentContent(navController: NavController, totalBill: String, viewModel: CartScreenViewModel) {
    var listOfProducts: List<Product> = emptyList()
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val currentDateAndTime = sdf.format(Date())
    var otpValue by remember { mutableStateOf("") }
    val orderId by remember { mutableStateOf(0) }

    val paymentSDK: PaymentInterface = Payment()

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfProducts = viewModel.data.value.data!!.toList()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 26.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter One Time Password",
            style = MaterialTheme.typography.titleLarge)

        Text(modifier = Modifier
            .padding(top = 26.dp),
            text = "Please enter the the 6-digit verification code sent to your phone number.",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        OtpTextField(modifier = Modifier
            .padding(top = 26.dp),
            otpText = otpValue,
            onOtpTextChange = { value, otpInputFilled ->
                otpValue = value
            }
        ) {
            Log.d("TAG", "PaymentContent: Otp entered")
            //show loader first


            // Confirm payment
            paymentSDK.confirmPayment("123456", object : PaymentCallback {
                override fun onSuccess(message: String?) {
                    // Handle success
                    Log.d("confirmPayment", message!!)
                    val products = OrderHistoryItem(
                        orderId = (orderId+1).toString(),
                        userId = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                        products = listOfProducts,
                        date = currentDateAndTime,
                        totalBill = totalBill
                    )
                    saveOrderHistoryInDatabase(products)

                    /*for (product in listOfProducts) {
                        deleteProductFromDatabase(product)
                    }*/

                    //Go to order placed screen
                    navController.navigate(ShoppingScreens.OrderPlacedScreen.name)
                }

                override fun onFailure(error: String?) {
                    // Handle failure
                    Log.e("confirmPayment", error!!)
                }
            })
        }

        Text(modifier = Modifier
            .padding(top = 8.dp),
            text = buildAnnotatedString {
                append("Didn't receive the code? ")

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Red.copy(alpha = 0.5f)
                )
            ) {
                append("Resend")
            }
        })
    }
}

fun saveOrderHistoryInDatabase(item: OrderHistoryItem) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("order_history")

    if (item.toString().isNotEmpty()) {
        dbCollection.add(item)
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

