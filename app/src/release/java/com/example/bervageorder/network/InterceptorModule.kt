package com.example.bervageorder.network

import okhttp3.logging.HttpLoggingInterceptor

object InterceptorModule {
    @OkHttpLoggingInterceptor
    fun getHttpLoggingInterceptorDebug(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }
}
