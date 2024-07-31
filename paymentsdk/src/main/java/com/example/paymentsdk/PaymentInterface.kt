package com.example.paymentsdk

interface PaymentInterface {
    fun startPayment(
        cardNo: String?,
        expDate: String?,
        cvv: String?,
        amount: Double,
        callback: PaymentCallback?
    )

    fun confirmPayment(
        otp: String?,
        callback: PaymentCallback?
    )
}