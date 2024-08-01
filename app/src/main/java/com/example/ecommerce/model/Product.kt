package com.example.ecommerce.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class Product(
    @Exclude var id: String? = null,

    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String? = null,

    @get:PropertyName("short_description")
    @set:PropertyName("short_description")
    var shortDescription: String? = "Description",

    @get:PropertyName("price")
    @set:PropertyName("price")
    var price: String? = null,

    @get:PropertyName("price_with_decimal")
    @set:PropertyName("price_with_decimal")
    var priceWithDecimal: Double? = null,

    @get:PropertyName("image")
    @set:PropertyName("image")
    var image: String? = null,

    @get:PropertyName("quantity")
    @set:PropertyName("quantity")
    var quantity: String? = null,

    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String? = null,
)
