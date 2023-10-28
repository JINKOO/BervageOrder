package com.example.bervageorder.domain.model

import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType
import java.text.DecimalFormat

data class Menu(
    val id: String = "",
    val type: MenuType = MenuType.NONE,
    val name: String = "",
    val temperature: TemperatureType = TemperatureType.NONE,
    val price: Int = 0,
    val isCaffeine: Boolean = false
) {
    val isDefaultOption: Boolean
        get() = when(temperature) {
            TemperatureType.ICE, TemperatureType.HOT, TemperatureType.BOTH -> true
            else -> false
        }

    val isIceQuantity: Boolean
        get() = when(type) {
            MenuType.COFFEE, MenuType.ADE -> true
            else -> false
        }

    val priceFormatString: String
        get() = price.toDecimalFormat()

    val caffeine: Caffeine
        get() = if (isCaffeine) Caffeine.CAFFEINE else Caffeine.NONE

    companion object {
        operator fun invoke(menuEntity: MenuEntity): Menu {
            return Menu(
                // TODO 2회차 질문 :: API Call 시, Entity가 null인 타입은 defaut로 어떻게 처리를 해야하는지
                id = menuEntity.id.orEmpty(),
                type = menuEntity.type ?: MenuType.NONE,
                name = menuEntity.name.orEmpty(),
                temperature = menuEntity.temperature ?: TemperatureType.NONE,
                price = menuEntity.price ?: 0,
                isCaffeine = menuEntity.isCaffeine ?: false
            )
        }
    }
}

fun Int.toDecimalFormat(): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(this)
}