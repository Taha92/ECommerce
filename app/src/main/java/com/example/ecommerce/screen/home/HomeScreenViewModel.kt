package com.example.ecommerce.screen.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.ProductItem
import com.example.ecommerce.model.ProductXX
import com.example.ecommerce.repository.FireRepository
import com.example.ecommerce.repository.ProductRepository
import com.example.jetareader.data.DataOrException
import com.example.jetareader.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FireRepository
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
}*/


@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: ProductRepository)
    : ViewModel() {
    var list: List<ProductXX> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)
    init {

        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                when(val response = repository.getProducts()) {
                    is Resource.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error -> {
                        isLoading = false
                        Log.e("Network", "Products: Failed getting products", )
                    }
                    else -> {isLoading = false}
                }

            }catch (exception: Exception){
                isLoading = false
                Log.d("Network", "Products: ${exception.message.toString()}")
            }

        }
    }
}