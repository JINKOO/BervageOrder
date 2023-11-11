package com.example.bervageorder.data.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 *  JSON Polymorphism
 *   - 상속관계에 있는 Reponse값들을 파싱하기 위해 사용
 */

@Serializable
sealed class Response

@Serializable
data class ErrorResponse(
    val code: Int,
    val message: String
): Response()

@Serializable
data class SuccessResponse<T>(
    val data: List<T>,
): Response()

@Serializable
data class PagingResponse<T>(
    val data: List<T>,
    val next: String?,
    val prev: String?
): Response()

val module = SerializersModule {
    polymorphic(Response::class) {
        subclass(Response::class)
        subclass(SuccessResponse::class)
        subclass(PagingResponse::class)
    }
}

val format = Json { serializersModule = module }
