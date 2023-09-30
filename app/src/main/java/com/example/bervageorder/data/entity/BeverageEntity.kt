package com.example.bervageorder.data.entity


data class BeverageEntity(
    val type: MenuType? = null,
    val name: String? = null,
    val temperature: TemperatureType? = null,
    val price: String? = null,
    val isCaffeine: Boolean? = null
)

enum class MenuType {
    COFFEE,
    ADE,
    TEA,
    DESSERT
}

enum class TemperatureType {
    ICE,
    HOT,
    NONE
}