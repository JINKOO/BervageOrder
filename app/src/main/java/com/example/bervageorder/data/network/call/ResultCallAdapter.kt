package com.example.bervageorder.data.network.call

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResultCallAdapter<R : Any>(
    private val type: Type,
    private val maxRetryCount: Int,
) : CallAdapter<R, Call<Result<R>>> {
    override fun responseType(): Type = type

    override fun adapt(call: Call<R>): Call<Result<R>> = ResultCall(call, maxRetryCount)
}
