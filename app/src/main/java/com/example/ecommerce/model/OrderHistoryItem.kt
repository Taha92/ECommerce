package com.example.ecommerce.model

import com.google.firebase.firestore.PropertyName

data class OrderHistoryItem(
    @get:PropertyName("order_id")
    @set:PropertyName("order_id")
    var orderId: String? = null,

    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String? = null,

    @get:PropertyName("products")
    @set:PropertyName("products")
    var products: List<Product>? = null,

    @get:PropertyName("date")
    @set:PropertyName("date")
    var date: String? = null,

    @get:PropertyName("total_bill")
    @set:PropertyName("total_bill")
    var totalBill: String? = null,
)
