package com.example.bervageorder.network

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class LogInterceptor

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UserAgentInterceptor

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UserTokenInterceptor
