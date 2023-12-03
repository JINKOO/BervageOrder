package com.example.bervageorder.data.network.call

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ResultCall<T : Any>(
    private val call: Call<T>,
    private val maxRetryCount: Int,
    private val retryCount: Int = 0,
) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val networkResult = try {
                        val body = response.body()
                        if (response.isSuccessful && body != null) {
                            Result.success(body)
                        } else {
                            Result.failure(Throwable("${response.code()} : ${response.message()}"))
                        }
                    } catch (e: HttpException) {
                        Result.failure(Throwable("${response.code()} : ${response.message()}"))
                    } catch (e: Throwable) {
                        Result.failure(e)
                    }

                    callback.onResponse(this@ResultCall, Response.success(networkResult))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (retryCount < maxRetryCount) {
                        clone().enqueue(callback)
                    }
                    val networkResult = Result.failure<T>(t)
                    callback.onResponse(this@ResultCall, Response.success(networkResult))
                }
            },
        )
    }

    override fun clone(): Call<Result<T>> {
        return ResultCall(call.clone(), maxRetryCount, retryCount + 1)
    }

    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException("ResultCall doesn't support execute")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun request(): Request = call.request()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun timeout(): Timeout = call.timeout()
}
