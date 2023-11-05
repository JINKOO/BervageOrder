package com.example.bervageorder.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(
    private val userToken: Flow<String?>,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request = chain.request()

        if ("true" in request.headers.values("NO-AUTH")) {
            return@runBlocking chain.proceed(request)
        }

        if (request.url.toString().contains("naver")) {
            return@runBlocking chain.proceedWithToken(request, "naverToken")
        }

        return@runBlocking chain.proceedWithToken(request, userToken.firstOrNull())
    }

    private fun Interceptor.Chain.proceedWithToken(req: Request, token: String?): Response {
        return req.newBuilder()
            .apply {
                if (!token.isNullOrBlank()) {
                    addHeader(AUTHORIZATION, "Bearer $token")
                }
            }
            .build()
            .let(::proceed)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}
