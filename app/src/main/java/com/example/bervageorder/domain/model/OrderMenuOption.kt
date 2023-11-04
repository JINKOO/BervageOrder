package com.example.bervageorder.domain.model

/**
 *  현재 선택한 메뉴에 대한 Option 선택 DTO
 */
data class OrderMenuOption(
    val temperature: Temperature = Temperature.NONE,
    val caffeine: Caffeine = Caffeine.NONE,
    val iceQuantity: IceQuantity = IceQuantity.NONE
)