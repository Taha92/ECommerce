package com.example.ecommerce.screen.history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.model.OrderHistoryItem
import com.example.ecommerce.model.Product
import com.example.ecommerce.repository.FireRepository
import com.example.ecommerce.repository.HistoryRepository
import com.example.jetareader.data.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(private val repository: HistoryRepository
): ViewModel() {

    val data: MutableState<DataOrException<List<OrderHistoryItem>, Boolean, Exception>>
            = mutableStateOf(
        DataOrException(listOf(), true, Exception())
    )

    init {
        getAllOrdersFromDatabase()
    }

    private fun getAllOrdersFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllOrderHistoryFromDatabase()

            if (!data.value.data.isNullOrEmpty()) {
                data.value.loading = false
            }
        }
    }
}