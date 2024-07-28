package com.example.ecommerce.screen.cart

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.model.Product
import com.example.ecommerce.repository.FireRepository
import com.example.jetareader.data.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(private val repository: FireRepository
): ViewModel() {

    val data: MutableState<DataOrException<List<Product>, Boolean, Exception>>
        = mutableStateOf(
            DataOrException(listOf(), true, Exception())
        )

    init {
        getAllProductsFromDatabase()
    }

    private fun getAllProductsFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllProductsFromDatabase()

            if (!data.value.data.isNullOrEmpty()) {
                data.value.loading = false
            }
        }
    }
}