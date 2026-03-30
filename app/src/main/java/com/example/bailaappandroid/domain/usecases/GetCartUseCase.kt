package com.example.bailaappandroid.domain.usecases

import com.example.bailaappandroid.data.remote.dto.CartResponse
import com.example.bailaappandroid.data.repository.CartRepository

class GetCartUseCase(
    private val repository: CartRepository
) {

    suspend fun execute(): CartResponse {
        return repository.getCart()
    }
}