package com.example.ecommerce.repository

import android.util.Log
import com.example.ecommerce.model.ProductXX
import com.example.ecommerce.network.ProductsApi
import com.example.jetareader.data.Resource
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductsApi) {

    suspend fun getProducts(): Resource<List<ProductXX>> {

        return try {
            Resource.Loading(data = true)

            val itemList = api.getAllProducts()
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList[0].products)

        }catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }

    }
}