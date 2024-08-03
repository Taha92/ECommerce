package com.example.ecommerce.screen.otp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.ecommerce.component.getNextOrderId
import com.example.ecommerce.model.OrderHistoryItem
import com.example.ecommerce.model.Product
import com.example.ecommerce.navigation.ShoppingScreens
import com.example.ecommerce.screen.cart.CartScreenViewModel
import com.example.ecommerce.util.Constants
import com.example.paymentsdk.Payment
import com.example.paymentsdk.PaymentCallback
import com.example.paymentsdk.PaymentInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun OtpScreen(navController: NavHostController, totalBill: String, cartViewModel: CartScreenViewModel) {
    var loading by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        ShoppingAppBar(title = "Payment",
            isMainScreen = false,
            showProfile = false,
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
            //payment content
            PaymentContent(navController, totalBill, cartViewModel) { loading = true }
        }
    }
}

@Composable
fun PaymentContent(navController: NavController, totalBill: String, viewModel: CartScreenViewModel, onLoadingChanged: (Boolean) -> Unit) {
    var listOfProducts: List<Product> = emptyList()
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss z")
    val currentDateAndTime = sdf.format(Date())
    var otpValue by remember { mutableStateOf("") }
    //var loading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var orderId = 0

    val paymentSDK: PaymentInterface = Payment()

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfProducts = viewModel.data.value.data!!.toList()
    }

    getNextOrderId { id ->
        orderId = id!!.toInt() + 1
    }

    if (viewModel.data.value.loading!!) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "Loading...")
        }
    } else {
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
                //show loader first
                onLoadingChanged(true)
                //loading = true

                // Confirm payment
                paymentSDK.confirmPayment(otpValue, object : PaymentCallback {
                    override fun onSuccess(message: String?) {
                        // Handle success
                        onLoadingChanged(false)
                        //loading = false
                        Log.d("confirmPayment", message!!)
                        val products = OrderHistoryItem(
                            orderId = orderId.toString(),
                            userId = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                            products = listOfProducts,
                            date = currentDateAndTime,
                            totalBill = totalBill
                        )
                        //Save order for history
                        saveOrderHistoryInDatabase(products)

                        //Empty cart
                        for (product in listOfProducts) {
                            deleteProductFromDatabase(product)
                        }

                        //Go to order placed screen
                        navController.navigate(ShoppingScreens.OrderPlacedScreen.name)
                    }

                    override fun onFailure(error: String?) {
                        // Handle failure
                        onLoadingChanged(false)
                        //loading = false
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
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
}

fun saveOrderHistoryInDatabase(item: OrderHistoryItem) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection(Constants.ORDER_HISTORY)

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

/*
fun saveOrderHistoryInDatabase(item: OrderHistoryItem) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection(Constants.ORDER_HISTORY)

    val orderId = item.orderId
    val query = dbCollection.whereEqualTo("order_id", orderId)

    query.get()
        .addOnSuccessListener { querySnapshot ->
            if (querySnapshot.isEmpty) {
                dbCollection.add(item)
                    .addOnSuccessListener { documentRef ->
                        val docId = documentRef.id
                        dbCollection.document(docId)
                            .update("id", docId)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("Success", "SaveToFirebase: Saved Successfully!")
                                }
                            }
                            .addOnFailureListener {
                                Log.d("Error", "Error updating doc")
                            }
                    }
                    .addOnFailureListener {
                        Log.d("Error", "Error adding document")
                    }
            } else {
                // Document already exists
                Log.d("Info", "Document with orderId $orderId already exists.")
            }
        }
        .addOnFailureListener {
            Log.d("Error", "Error querying Firestore")
        }
}
*/


