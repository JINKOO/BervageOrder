package com.example.bervageorder

import okhttp3.logging.HttpLoggingInterceptor

class HttpLoggingInterceptorDebug {
    companion object {
        fun getHttpLoggingInterceptorDebug(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        }
    }
}