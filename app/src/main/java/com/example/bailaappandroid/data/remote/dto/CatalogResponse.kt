package com.example.bailaappandroid.data.remote.dto

import com.example.bailaappandroid.data.model.Collection
import com.example.bailaappandroid.data.model.Outfit

data class CatalogResponse (
    val collections: List<Collection>,
    val outfits: List<Outfit>
)