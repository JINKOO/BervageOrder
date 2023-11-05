package com.example.bervageorder.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface TestApi {

    @Headers("NO-AUTH: true")
    @POST("/api/user/getToken")
    suspend fun getToken(): String

    @GET("/api/naver/getToken")
    suspend fun getNaverToken(): String
}
