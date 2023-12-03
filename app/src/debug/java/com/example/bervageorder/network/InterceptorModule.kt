package com.example.bervageorder.network

import com.example.bervageorder.data.network.OkHttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(SingletonComponent::class)
@Module
object InterceptorModule {
    @Provides
    @OkHttpLoggingInterceptor
    fun provideOkHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
}
