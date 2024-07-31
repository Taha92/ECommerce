package com.example.ecommerce.model

data class CardInfo(
    var holderName: String? = null,
    var holderNumber: String? = null,
    var expiryMonth: String? = null,
    var expiryYear: String? = null,
    var cardCvv: String? = null,
)
