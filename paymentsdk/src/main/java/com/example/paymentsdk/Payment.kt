package com.example.paymentsdk

import android.os.Handler
import android.os.Looper


class Payment : PaymentInterface {
    override fun startPayment(
        cardNo: String?,
        expDate: String?,
        cvv: String?,
        amount: Double,
        callback: PaymentCallback?
    ) {
        // Simulate network delay
        Handler(Looper.getMainLooper()).postDelayed({
            // Dummy logic to simulate payment processing
            if (cardNo == "1234567890123456") {
                callback!!.onSuccess("Payment initiated. Enter OTP to confirm.")
            } else {
                callback!!.onFailure("Invalid card details.")
            }
        }, 2000) // 2 seconds delay
    }

    override fun confirmPayment(otp: String?, callback: PaymentCallback?) {
        // Simulate network delay
        Handler(Looper.getMainLooper()).postDelayed({
            // Dummy logic to simulate OTP verification
            if (otp == "123456") {
                callback!!.onSuccess("Payment successful!")
            } else {
                callback!!.onFailure("Invalid OTP.")
            }
        }, 2000) // 2 seconds delay
    }
}