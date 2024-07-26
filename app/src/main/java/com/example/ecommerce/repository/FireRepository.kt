package com.example.ecommerce.repository

import com.example.ecommerce.model.Product
import com.example.jetareader.data.DataOrException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(private val queryProduct: Query) {
    suspend fun getAllProductsFromDatabase(): DataOrException<List<Product>, Boolean, Exception> {
        val dataOrException = DataOrException<List<Product>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = queryProduct.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(Product::class.java)!!
            }

            if (!dataOrException.data.isNullOrEmpty()) {
                dataOrException.loading = false
            }

        } catch (ex: FirebaseFirestoreException) {
            dataOrException.e = ex
        }

        return dataOrException
    }
}