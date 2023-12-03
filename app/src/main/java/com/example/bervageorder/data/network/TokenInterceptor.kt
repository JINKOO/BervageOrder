package com.example.bervageorder.data.network

import com.example.bervageorder.data.network.api.AuthAPI
import com.example.bervageorder.data.repository.TokenSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Provider

/**
 *  UserToken을 Header에 저장하는 Interceptor
 *
 *  경우에 따라서, Token을 넣어야 하는 경우,
 */
class TokenInterceptor @Inject constructor(
    private val tokenSource: TokenSource,
    private val authAPI: Provider<AuthAPI>,
) : Interceptor {

    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request = chain.request()
        val accessToken = tokenSource.accessToken.firstOrNull()

        // 1. API를 호출하는데, Token이 필요없는 경우, 그대로 return
        if ("true" in request.headers.values("NO-AUTH")) {
            return@runBlocking chain.proceed(request = request)
        }

        // 2. 예를 들어, 다른 Token이 필요한 경우 (naver sdk token)
        if (request.url.toString().contains("naver")) {
            return@runBlocking chain.proceedWithToken(request = request, token = "naver_token")
        }

        val response = chain.proceedWithToken(request = request, token = accessToken)

        // 2-1. 정상 응답인 경우 그대로 return
        // 2-2. Token이 없는 경우(Logout, 처음 사용자), 그대로 return
        if (response.code != HttpURLConnection.HTTP_UNAUTHORIZED || accessToken == null) {
            return@runBlocking response
        }

        // 3. Token이 만료된 경우, Token을 재발급 받는다.
        val (newAccessToken, newRefreshToken) = mutex.withLock {
            val currentAccessToken = tokenSource.accessToken.firstOrNull()
            val currentRefreshToken = tokenSource.refreshToken.firstOrNull()

            if (currentAccessToken.isNullOrBlank() || currentRefreshToken.isNullOrBlank()) {
                return@withLock Token(null, null)
            }

            val result = authAPI.get().refresh().getOrNull()

            return@withLock result?.let {
                Token(it[0], it[1])
            } ?: run {
                Token(null, null)
            }
        }

        return@runBlocking if (newAccessToken != null) {
            // 3-1. Token 재발급 성공
            // 3-1-1. Token을 저장한다.
            // 3-1-2. 새로운 Token을 Header에 넣어서 다시 호출한다.
            tokenSource.setToken(access = newAccessToken, refresh = newRefreshToken)
            chain.proceedWithToken(request = request, token = newAccessToken)
        } else {
            // 3-2. Token 재발급 실패
            // 3-2-1. 로그인 화면으로 이동한다.
            // 3-2-2. Token을 삭제한다.
            tokenSource.removeToken()
            response
        }
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

    data class Token(
        val accessToken: String?,
        val refreshToken: String?,
    )
}
