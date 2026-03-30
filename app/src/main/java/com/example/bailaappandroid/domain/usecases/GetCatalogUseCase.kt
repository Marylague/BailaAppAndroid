package com.example.bailaappandroid.domain.usecases

import com.example.bailaappandroid.data.remote.dto.CatalogResponse
import com.example.bailaappandroid.data.repository.CatalogRepository

class GetCatalogUseCase(
    private val repository: CatalogRepository
) {

    suspend fun execute(): CatalogResponse {
        return repository.getCatalog()
    }
}