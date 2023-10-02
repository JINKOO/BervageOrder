package com.example.bervageorder.domain.model

import com.example.bervageorder.data.entity.TemperatureType

data class Beverage(
    val name: String,
    val temperature: TemperatureType,
    val price: String,
    val isCaffeine: Boolean
) {
}