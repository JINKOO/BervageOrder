package com.example.bervageorder.network

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class ResultCallAdapter<R>(
    private val tokenRepository: TokenRepository,
    private val responseType: Type,
    private val maxRetryCount: Int,
) : CallAdapter<R, Call<Result<R>>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): Call<Result<R>> {
        return ResultCall(
            call = call,
            tokenRepository = tokenRepository,
            remainingRetryCount = maxRetryCount,
        )
    }

    class Factory(
        private val tokenRepository: TokenRepository,
        private val maxRetryCount: Int,
    ) : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit,
        ): CallAdapter<*, *>? {
            if (getRawType(returnType).kotlin != Call::class) {
                return null
            }

            check(returnType is ParameterizedType) {
                "return type must be parameterized as Call<Result<<Foo>> or Call<Result<out Foo>>"
            }

            val responseType = getParameterUpperBound(0, returnType)

            if (getRawType(responseType).kotlin != Result::class) {
                return null
            }

            check(responseType is ParameterizedType) {
                "Response must be parameterized as Result<Foo> or Result<out Foo>"
            }

            val resultType = getParameterUpperBound(0, responseType)

            return ResultCallAdapter<Any>(
                tokenRepository = tokenRepository,
                responseType = resultType,
                maxRetryCount = maxRetryCount,
            )
        }
    }
}

class ResultCall<T>(
    private val call: Call<T>,
    private val tokenRepository: TokenRepository,
    private val remainingRetryCount: Int,
) : Call<Result<T>> {
    override fun clone(): Call<Result<T>> {
        return ResultCall(
            call = call.clone(),
            tokenRepository = tokenRepository,
            remainingRetryCount = remainingRetryCount - 1,
        )
    }

    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException("ResultCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    runCatching {
                        val httpStatusCode = response.code()

                        when {
                            !call.request().isRefreshToken() &&
                                httpStatusCode == HTTP_UNAUTHORIZED -> {
                                // token 갱신
                                tokenRepository.getRefreshToken()

                                val newCallResponse = call.clone().execute()
                                when {
                                    newCallResponse.isSuccessful -> when (
                                        val body =
                                            newCallResponse.body()
                                    ) {
                                        null -> Exception("body is null")
                                        else -> body
                                    }

                                    else -> Exception("refresh token invalid")
                                }
                            }

                            response.isSuccessful -> when (val body = response.body()) {
                                null -> Exception("body is null")
                                else -> body
                            }

                            else -> Exception("response is not successful")
                        }
                    }
                        .let { result ->
                            callback.onResponse(this@ResultCall, Response.success(result))
                        }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (remainingRetryCount < MIN_RETRY_COUNT) {
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(t)),
                        )
                        return
                    }

                    when (t) {
                        is SocketException,
                        is SocketTimeoutException,
                        is SSLHandshakeException,
                        is UnknownHostException,
                        -> {
                            // 재실행 시도 횟수 감소
                            clone().enqueue(callback)
                        }

                        else -> {
                            // 재실행 안하고 그냥 실패 처리
                            callback.onResponse(
                                this@ResultCall,
                                Response.success(Result.failure(t)),
                            )
                        }
                    }
                }
            },
        )
    }

    private fun Request.isRefreshToken(): Boolean {
        return method == "POST" &&
            url.encodedPath.contains("/auth/refresh")
    }

    companion object {
        private const val MIN_RETRY_COUNT = 1
    }
}
