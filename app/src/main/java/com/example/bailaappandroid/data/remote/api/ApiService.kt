package com.example.bailaappandroid.data.remote.api

import com.example.bailaappandroid.data.remote.dto.CartResponse
import com.example.bailaappandroid.data.remote.dto.CatalogResponse
import com.example.bailaappandroid.data.remote.dto.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("catalog")
    suspend fun getCatalog() : CatalogResponse

    @GET("server/echo/{echoPath}")
    suspend fun getScreenConfig(
        @Path("echoPath") echoPath: String
    ): CartResponse

    @GET("profile")
    suspend fun getProfile(): ProfileResponse
}