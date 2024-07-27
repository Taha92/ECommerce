package com.example.ecommerce.model

import com.google.firebase.firestore.Exclude

data class Product(
    @Exclude var id: String? = null,
    var name: String? = null,
    var price: String? = null,
    var image: String? = null,
    var quantity: String? = null,
)
