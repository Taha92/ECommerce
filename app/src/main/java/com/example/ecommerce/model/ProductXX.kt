package com.example.ecommerce.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class ProductXX(
    val attribute: String = "",
    val id: String = "",
    val imageURL: String,
    val name: String,
    val price: Double,
    val priceText: String = "",
    //val priceWithDecimal: Double,
    //val price: String = "",
    val shortDescription: String = "Description",
    //@SerializedName("image")
    val thumbnailURL: String = ""
) : java.io.Serializable