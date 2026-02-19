package com.example.bailaappandroid

data class Collection (
    val id: Long,
    val name: String
)

data class Outfit (
    val id: Long,
    val name: String,
    val price: String,
    val imageUrl: String,
    val collectionId: Long
)