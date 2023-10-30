package com.example.bervageorder.data.entity

data class OrderEntity(
    val id: String? = null,
    val name: String? = null,
    val temperature: TemperatureType? = null,
    val price: Int? = null,
    val isCaffeine: Boolean? = null
)
