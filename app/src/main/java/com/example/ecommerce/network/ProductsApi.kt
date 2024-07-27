package com.example.ecommerce.network

import com.example.ecommerce.model.ProductItem
import com.example.ecommerce.model.ProductXX
import com.example.jetareader.data.Resource
import retrofit2.http.GET

interface ProductsApi {

    @GET("products")
    suspend fun getAllProducts(): List<ProductItem>
}