package com.example.bailaappandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    var profile by mutableStateOf<ProfileResponse?>(null)
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val response = api.getProfile()
                profile = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}