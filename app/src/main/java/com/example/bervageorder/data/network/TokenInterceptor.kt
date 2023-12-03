package com.example.bervageorder.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 *  UserToken을 Header에 저장하는 Interceptor
 *
 *  경우에 따라서, Token을 넣어야 하는 경우,
 */
class TokenInterceptor(
    private val userToken: Flow<String?>
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request = chain.request()
        val accessToken = userToken.firstOrNull()

        // 1. API를 호출하는데, Token이 필요없는 경우, 그대로 return
        if ("true" in request.headers.values("NO-AUTH")) {
            return@runBlocking chain.proceed(request = request)
        }

        // 2. 예를 들어, 다른 Token이 필요한 경우 (naver sdk token)
        if (request.url.toString().contains("naver")) {
            return@runBlocking chain.proceedWithToken(request = request, token = "naver_token")
        }
        return@runBlocking chain.proceedWithToken(request = request, token = accessToken)
    }


    private fun Interceptor.Chain.proceedWithToken(request: Request, token: String?): Response {
        return request.newBuilder().apply {
            if (token.isNullOrBlank().not()) {
                addHeader(AUTHORIZATION, "Bearer $token")
            }
        }.build().let(::proceed)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}