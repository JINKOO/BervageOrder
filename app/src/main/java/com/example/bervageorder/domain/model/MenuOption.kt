package com.example.bervageorder.domain.model

/**
 *  사용자에게 옵션 입력 받는 DTO
 */
data class MenuOption(
    val temperature: Temperature,
    val caffeine: Caffeine,
    val iceQuantity: IceQuantity
)