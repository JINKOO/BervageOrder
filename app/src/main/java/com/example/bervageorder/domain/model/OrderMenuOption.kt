package com.example.bervageorder.domain.model

/**
 *  사용자에게 옵션 입력 받는 DTO
 */
data class OrderMenuOption(
    val temperature: Temperature = Temperature.NONE,
    val caffeine: Caffeine = Caffeine.NONE,
    val iceQuantity: IceQuantity = IceQuantity.NONE
)