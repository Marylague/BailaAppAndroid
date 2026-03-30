package com.example.bailaappandroid.data.repository

import com.example.bailaappandroid.data.remote.api.ApiService
import com.example.bailaappandroid.data.remote.dto.CatalogResponse

class CatalogRepository(
    private val api: ApiService
) {

    suspend fun getCatalog(): CatalogResponse {
        return api.getCatalog()
    }
}