package com.example.bervageorder.network

class ServerList(
    private val testValue: String,
) {
    fun getBaseUrl(): String {
        return ServerType.values().find { it.name == testValue }?.url ?: ""
    }

    fun getSecondBaseUrl(): String {
        return ServerType.values().find { it.name == testValue }?.secondUrl ?: ""
    }

    private enum class ServerType(
        val url: String,
        val secondUrl: String,
    ) {
        DEV("https://dev-api.example.com", "https://dev-api2.example.com"),
        STG("https://stg-api.example.com", "https://stg-api2.example.com"),
        PROD("https://api.example.com", "https://api2.example.com"),
    }
}
