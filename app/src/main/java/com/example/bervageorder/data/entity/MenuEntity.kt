package com.example.bervageorder.data.entity

import androidx.compose.runtime.Composable

data class MenuEntity(
    val id: String? = null,
    val type: MenuType? = null,
    val name: String? = null,
    val temperature: TemperatureType? = null,
    val price: String? = null,
    val isCaffeine: Boolean? = null,
)

enum class MenuType {
    COFFEE,
    ADE,
    TEA,
    DESSERT,
    NONE,
}

@Composable
fun MenuType.getPadding(isCompact: Boolean) {
}

enum class TemperatureType {
    ICE,
    HOT,
    BOTH,
    NONE,
}
