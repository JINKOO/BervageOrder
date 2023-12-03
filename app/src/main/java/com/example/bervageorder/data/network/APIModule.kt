package com.example.bervageorder.data.network

import com.example.bervageorder.data.network.api.AuthAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {
    @Provides
    @Singleton
    fun provideAuthAPI(
        retrofit: Retrofit,
    ): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }
}
