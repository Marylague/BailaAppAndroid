package com.example.bailaappandroid.presentation.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bailaappandroid.data.remote.api.RetrofitInstance
import com.example.bailaappandroid.data.remote.dto.ProfileResponse
import com.example.bailaappandroid.data.repository.ProfileRepository
import com.example.bailaappandroid.domain.usecases.GetProfileUseCase
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    var profile by mutableStateOf<ProfileResponse?>(null)
        private set

    private val api = RetrofitInstance.api
    private val repository = ProfileRepository(api)
    private val useCase = GetProfileUseCase(repository)

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                profile = useCase.execute()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}