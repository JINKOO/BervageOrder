package com.example.bervageorder.data.entity

import androidx.compose.runtime.Composable
import java.util.UUID

data class MenuEntity(
    val id: String = UUID.randomUUID().toString(), // 서버에서도 id로 줄 값이 없어서 임시로 UUID로 설정
    val name: String?,
    val price: Int?,
    val type: MenuType?,
    val temperature: TemperatureType?,
    val caffeine: CaffeineType?,
)

enum class MenuType {
    COFFEE,
    ADE,
    TEA,
    DESSERT,
    NONE,
}

/**
 *  해상도 대응
 */
@Composable
fun MenuType.getPadding(isCompact: Boolean) {
    // dp
}

enum class TemperatureType {
    ICE,
    HOT,
    BOTH,
    NONE,
    ;

    fun toDesireString(): String? {
        return when (this) {
            ICE -> "아이스"
            HOT -> "핫"
            else -> null
        }
    }
}

enum class CaffeineType {
    CAFFEINE,
    DECAFFEINE,
    BOTH,
    NONE,
    ;

    fun toDesireString(): String? {
        return when (this) {
            CAFFEINE -> "카페인"
            DECAFFEINE -> "디카페인"
            else -> null
        }
    }
}
