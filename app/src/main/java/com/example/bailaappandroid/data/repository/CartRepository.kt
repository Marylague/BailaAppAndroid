package com.example.bailaappandroid.data.repository

import com.example.bailaappandroid.data.remote.api.ApiService
import com.example.bailaappandroid.data.remote.dto.CartResponse

class CartRepository(
    private val api: ApiService
) {

    suspend fun getCart(path: String = "cart_v1"): CartResponse {
        return api.getScreenConfig(path)
    }
}