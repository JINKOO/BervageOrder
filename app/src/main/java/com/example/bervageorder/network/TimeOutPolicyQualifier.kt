package com.example.bervageorder.network

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class WriteTimeOut

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ReadTimeOut

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ConnectTimeOut
