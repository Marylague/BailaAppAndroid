package com.example.bailaappandroid.data.repository

import com.example.bailaappandroid.data.remote.api.ApiService
import com.example.bailaappandroid.data.remote.dto.ProfileResponse

class ProfileRepository(
    private val api: ApiService
) {

    suspend fun getProfile(): ProfileResponse {
        return api.getProfile()
    }
}