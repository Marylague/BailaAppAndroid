package com.example.bailaappandroid.domain.usecases

import com.example.bailaappandroid.data.remote.dto.ProfileResponse
import com.example.bailaappandroid.data.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend fun execute(): ProfileResponse {
        return repository.getProfile()
    }
}