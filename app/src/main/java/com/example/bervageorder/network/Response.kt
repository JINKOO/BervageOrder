package com.example.bervageorder.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
sealed class Response

@Serializable
data class ErrorResponse(
    val code: Int,
    val message: String,
) : Response()

@Serializable
data class SuccessResponse<T>(
    val data: List<T>,
) : Response()

@Serializable
data class PagingResponse<T>(
    val data: List<T>,
    val next: String?,
    val prev: String?,
) : Response()

val module = SerializersModule {
    polymorphic(Response::class) {
        subclass(ErrorResponse::class)
        subclass(SuccessResponse::class)
        subclass(PagingResponse::class)
    }
}

val format = Json { serializersModule = module }
