package com.example.bailaappandroid.data.repository

import com.example.bailaappandroid.data.remote.api.ApiService
import com.example.bailaappandroid.data.remote.dto.CartResponse

class CartRepository(
    private val api: ApiService
) {

    suspend fun getCart(): CartResponse {
        return api.getCart()
    }
}