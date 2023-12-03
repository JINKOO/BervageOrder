package com.example.bervageorder.data.network.api

import retrofit2.http.POST

interface AuthAPI {
    @POST("auth/login")
    suspend fun login(): Result<Boolean>

    @POST("auth/logout")
    suspend fun logout(): Result<Boolean>

    @POST("auth/refresh")
    suspend fun refresh(): Result<List<String>>
}
