package com.example.bervageorder.data.repository

import kotlinx.coroutines.flow.Flow

interface TokenSource {
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>

    suspend fun setToken(access: String?, refresh: String?)
    suspend fun removeToken()
}
