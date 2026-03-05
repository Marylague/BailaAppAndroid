package com.example.bailaappandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CatalogViewModel : ViewModel() {
    var collections by mutableStateOf<List<Collection>>(emptyList())
        private set
    var outfits by mutableStateOf<List<Outfit>>(emptyList())
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val response = api.getCatalog()
                collections = response.collections
                outfits = response.outfits
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}