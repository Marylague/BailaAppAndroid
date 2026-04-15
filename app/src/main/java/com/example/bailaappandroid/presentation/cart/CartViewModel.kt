package com.example.bailaappandroid.presentation.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bailaappandroid.data.remote.api.RetrofitInstance
import com.example.bailaappandroid.data.remote.dto.toDomain
import com.example.bailaappandroid.data.repository.CartRepository
import com.example.bailaappandroid.domain.usecases.GetCartUseCase
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    var uiState by mutableStateOf<List<CartBlock>>(emptyList())
        private set

    private val api = RetrofitInstance.api
    private val repository = CartRepository(api)
    private val useCase = GetCartUseCase(repository)

    init {
        loadScreen()
    }

    private fun loadScreen() {
        viewModelScope.launch {
            try {
                val response = useCase.execute("cart_v1")
                uiState = response.blocks.mapNotNull { it.toDomain() }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}