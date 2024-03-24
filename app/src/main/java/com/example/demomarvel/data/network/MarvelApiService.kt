package com.example.demomarvel.data.network

import com.example.demomarvel.data.network.response.MarvelCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {
    @GET("characters")
    suspend fun  getAllChars(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: String,
        @Query("limit") limit: String,

    ): Response<MarvelCharactersResponse>

    @GET("characters")
    suspend fun  getAllCharsWithName(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: String,
        @Query("name") name: String,
        @Query("limit") limit: String,

        ): Response<MarvelCharactersResponse>
}