package com.example.bailaappandroid.presentation.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bailaappandroid.data.model.Collection
import com.example.bailaappandroid.data.model.Outfit
import com.example.bailaappandroid.data.remote.api.RetrofitInstance
import com.example.bailaappandroid.data.repository.CatalogRepository
import com.example.bailaappandroid.domain.usecases.GetCatalogUseCase
import kotlinx.coroutines.launch

class CatalogViewModel : ViewModel() {
    var collections by mutableStateOf<List<Collection>>(emptyList())
        private set
    var outfits by mutableStateOf<List<Outfit>>(emptyList())
        private set

    private val api = RetrofitInstance.api

    private val repository = CatalogRepository(api)
    private val useCase = GetCatalogUseCase(repository)

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val response = useCase.execute()
                collections = response.collections
                outfits = response.outfits
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}