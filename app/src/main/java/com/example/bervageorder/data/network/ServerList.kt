package com.example.bervageorder.data.network

/**
 *  Dev, Beta, Prod에 따른 서버 환경 구성
 */
class ServerList(private val testValue: String) {

    fun getBaseUrl(): String {
        return ServerType.values().find {
            testValue == it.name
        }?.url ?: ""
    }

    fun getSecondBaseUrl(): String = ServerType.values().find {
        testValue == it.name
    }?.url ?: ""


    enum class ServerType(
        val url: String,
        val secondUrl: String
    ) {
        DEV(url = "", secondUrl = ""),
        BETA(url = "", secondUrl = ""),
        PROD(url = "", secondUrl = "")
    }
}