package com.example.bervageorder.data.network

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class OkHttpLoggingInterceptor

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UserAgentInterceptor

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UserTokenInterceptor