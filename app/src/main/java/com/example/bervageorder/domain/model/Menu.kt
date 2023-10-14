package com.example.bervageorder.domain.model

import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType

data class Menu(
    val id: String = "",
    val type: MenuType = MenuType.NONE,
    val name: String = "",
    val temperature: TemperatureType = TemperatureType.NONE,
    val price: String = "", // TODO 상식적인 타입으로 하자 INT로
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

    companion object {
        operator fun invoke(menuEntity: MenuEntity): Menu {
            return Menu(
                // TODO 질문 :: API Call 시, Entity가 null인 타입은 defaut로 어떻게 처리를 해야하는지
                id = menuEntity.id.orEmpty(),
                type = menuEntity.type ?: MenuType.NONE,
                name = menuEntity.name.orEmpty(),
                temperature = menuEntity.temperature ?: TemperatureType.NONE,
                price = menuEntity.price.orEmpty(),
                isCaffeine = menuEntity.isCaffeine ?: false
            )
        }
    }
}