package com.example.ecommerce.model

import com.google.firebase.firestore.Exclude

data class Product(
    @Exclude var id: String? = null,
    var name: String? = null,
    var shortDescription: String? = "Description",
    var price: String? = null,
    var priceWithDecimal: Double? = null,
    var image: String? = null,
    var quantity: String? = null,
)
