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
import com.example.paymentsdk.Payment
import com.example.paymentsdk.PaymentCallback
import com.example.paymentsdk.PaymentInterface


@Composable
fun OtpScreen(navController: NavHostController, totalPrice: String) {
    Scaffold(topBar = {
        ShoppingAppBar(title = "Payment",
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
            //payment content
            PaymentContent(navController, totalPrice)
        }
    }
}

@Composable
fun PaymentContent(navController: NavController, totalPrice: String) {
    var otpValue by remember {
        mutableStateOf("")
    }

    val paymentSDK: PaymentInterface = Payment()

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
            //start payment
            paymentSDK.startPayment(
                "1234567890123456",
                "12/25",
                "123",
                100.0,
                object : PaymentCallback {
                    override fun onSuccess(message: String?) {
                        // Handle success, prompt user to enter OTP
                        Log.d("Payment", message!!)
                    }

                    override fun onFailure(error: String?) {
                        // Handle failure
                        Log.e("Payment", error!!)
                    }
                })


            // Confirm payment
            paymentSDK.confirmPayment("123456", object : PaymentCallback {
                override fun onSuccess(message: String?) {
                    // Handle success
                    Log.d("confirmPayment", message!!)
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

        /*Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            OtpTextField(
                otpText = otpValue,
                onOtpTextChange = { value, otpInputFilled ->
                    otpValue = value
                })
        }*/
    }

}
