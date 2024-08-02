package com.example.ecommerce.model

import kotlinx.serialization.Serializable


@Serializable
data class ProductXX(
    val attribute: String = "",
    val id: String = "",
    val imageURL: String,
    val name: String,
    val price: Double,
    val priceText: String = "",
    val shortDescription: String = "Description",
    val thumbnailURL: String = ""
) : java.io.Serializable