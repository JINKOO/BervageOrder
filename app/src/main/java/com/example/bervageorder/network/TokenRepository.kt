package com.example.bervageorder.network

interface TokenRepository {
    fun getAccessToken(): String
    fun getRefreshToken(): String

    fun setAccessToken(accessToken: String)
    fun setRefreshToken(refreshToken: String)
}
