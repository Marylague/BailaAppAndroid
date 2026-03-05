package com.example.bailaappandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    var cartItems by mutableStateOf<List<Outfit>>(emptyList())
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val response = api.getCart()
                cartItems = response.cartItems
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}