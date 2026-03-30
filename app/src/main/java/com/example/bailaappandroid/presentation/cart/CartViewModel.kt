package com.example.bailaappandroid.presentation.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bailaappandroid.data.model.Outfit
import com.example.bailaappandroid.data.remote.api.RetrofitInstance
import com.example.bailaappandroid.data.repository.CartRepository
import com.example.bailaappandroid.domain.usecases.GetCartUseCase
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    var cartItems by mutableStateOf<List<Outfit>>(emptyList())
        private set

    private val api = RetrofitInstance.api
    private val repository = CartRepository(api)
    private val useCase = GetCartUseCase(repository)

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val response = useCase.execute()
                cartItems = response.cartItems
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}