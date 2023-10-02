package com.example.bervageorder.domain.model

import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType

data class Menu(
    val menuType: MenuType,
    val name: String,
    val temperature: TemperatureType,
    val price: String,
    val isCaffeine: Boolean
) {
    companion object {
        operator fun invoke(menuEntity: MenuEntity): Menu {
            return Menu(
                // TODO API Call 시, Entity가 null인 타입은 defaut로 어떻게 처리를 해야하는지
                menuType = menuEntity.type ?: MenuType.NONE,
                name = menuEntity.name.orEmpty(),
                temperature = menuEntity.temperature ?: TemperatureType.NONE,
                price = menuEntity.price.orEmpty(),
                isCaffeine = menuEntity.isCaffeine ?: false
            )
        }
    }
}