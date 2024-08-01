package com.example.ecommerce.model

data class OrderHistoryItem(
    var orderId: String? = null,
    var products: List<Product>? = null,
    var dateTime: String? = null,
    var totalBill: String? = null,
)
