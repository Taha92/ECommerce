package com.example.paymentsdk

interface PaymentCallback {
    fun onSuccess(message: String?)
    fun onFailure(error: String?)
}