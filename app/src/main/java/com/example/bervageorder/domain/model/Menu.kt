package com.example.bervageorder.domain.model

import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType

data class Menu(
    val id: String = "",
    val menuType: MenuType = MenuType.NONE,
    val name: String = "",
    val temperature: TemperatureType = TemperatureType.NONE,
    val price: String = "",
    val isCaffeine: Boolean = false
) {
    companion object {
        operator fun invoke(menuEntity: MenuEntity): Menu {
            return Menu(
                // TODO API Call 시, Entity가 null인 타입은 defaut로 어떻게 처리를 해야하는지
                id = menuEntity.id.orEmpty(),
                menuType = menuEntity.type ?: MenuType.NONE,
                name = menuEntity.name.orEmpty(),
                temperature = menuEntity.temperature ?: TemperatureType.NONE,
                price = menuEntity.price.orEmpty(),
                isCaffeine = menuEntity.isCaffeine ?: false
            )
        }
    }
}