package com.example.ecommerce.screen.cart

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerce.R
import com.example.ecommerce.component.RoundedSubmitButton
import com.example.ecommerce.component.ShoppingAppBar
import com.example.ecommerce.model.CardInfo
import com.example.ecommerce.navigation.ShoppingScreens
import com.example.paymentsdk.Payment
import com.example.paymentsdk.PaymentCallback
import com.example.paymentsdk.PaymentInterface

@Composable
fun CheckoutScreen(
    navController: NavController,
    cardInfo: CardInfo,
    totalBill: String,
) {
    Scaffold(topBar = {
        ShoppingAppBar(title = "Checkout",
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
            //checkout content
            CheckoutContent(navController, cardInfo, totalBill)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CheckoutContent(
    navController: NavController = NavController(LocalContext.current),
    cardInfo: CardInfo,
    totalPrice: String
) {
    val totalPayable = (totalPrice.toDouble() * 10) / 100
    val formattedTotal = String.format("%.2f", totalPrice.toDouble() - totalPayable)
    var loading by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
                Text(text = "Loading...")
            }
        } else {
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
                            text = cardInfo.holderName.toString(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Text(
                            text = "************${cardInfo.holderNumber.toString().takeLast(4)}"
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            navController.navigate(ShoppingScreens.CardDetailScreen.name + "/${totalPrice}")
                            //navController.navigate(ShoppingScreens.CardDetailScreen.name)
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
                        text = String.format("₺%.2f", totalPrice.toDouble()),
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
                        text = "₺${formattedTotal}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            }

            Box(modifier = Modifier.weight(0.1f)) {
                RoundedSubmitButton(label = "Order and Pay") {
                    startPaymentProcess(navController, cardInfo, formattedTotal.toDouble()) { loading = true }
                }
            }
        }

    }
}

fun startPaymentProcess(
    navController: NavController,
    cardInfo: CardInfo,
    totalPayable: Double,
    onLoadingChanged: (Boolean) -> Unit
) {
    val paymentSDK: PaymentInterface = Payment()

    onLoadingChanged(true)

    //start payment
    paymentSDK.startPayment(
        cardInfo.holderNumber,
        "${cardInfo.expiryMonth}/${cardInfo.expiryYear.toString().takeLast(2)}",
        cardInfo.cardCvv,
        totalPayable,
        object : PaymentCallback {
            override fun onSuccess(message: String?) {
                // Handle success, prompt user to enter OTP
                onLoadingChanged(false)
                Log.d("Payment", message!!)
                navController.navigate(ShoppingScreens.OtpScreen.name + "/${totalPayable}")
                //navController.navigate(ShoppingScreens.OtpScreen.name)
            }

            override fun onFailure(error: String?) {
                // Handle failure
                onLoadingChanged(false)
                Log.e("Payment", error!!)
            }
        })
}

@Composable
fun BankCardDotGroup() {
    Canvas(
        modifier = Modifier.width(48.dp),
        onDraw = { // You can adjust the width as needed
            val dotRadius = 4.dp.toPx()
            val spaceBetweenDots = 0.dp.toPx()
            for (i in 0 until 4) { // Draw four dots
                drawCircle(
                    color = Color.Blue,
                    radius = dotRadius,
                    center = Offset(
                        x = i * (dotRadius * 2 + spaceBetweenDots) + dotRadius,
                        y = center.y
                    )
                )
            }
        })
}