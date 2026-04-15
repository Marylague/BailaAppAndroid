package com.example.bailaappandroid.domain.usecases

import com.example.bailaappandroid.data.remote.dto.CartResponse
import com.example.bailaappandroid.data.repository.CartRepository

class GetCartUseCase(
    private val repository: CartRepository
) {

    suspend fun execute(path: String = "cart_v1"): CartResponse {
        return repository.getCart(path)
    }
}