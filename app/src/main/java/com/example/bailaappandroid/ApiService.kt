package com.example.bailaappandroid
import retrofit2.http.GET

interface ApiService {
    @GET("catalog")
    suspend fun getCatalog() : CatalogResponse

    @GET("cart")
    suspend fun getCart() : CartResponse
}