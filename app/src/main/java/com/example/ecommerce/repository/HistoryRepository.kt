package com.example.ecommerce.repository

import com.example.ecommerce.model.OrderHistoryItem
import com.example.jetareader.data.DataOrException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val queryHistory: Query) {
    suspend fun getAllOrderHistoryFromDatabase(): DataOrException<List<OrderHistoryItem>, Boolean, Exception> {
        val dataOrException = DataOrException<List<OrderHistoryItem>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = queryHistory.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(OrderHistoryItem::class.java)!!
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