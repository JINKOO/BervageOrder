package com.example.bervageorder.domain.model

import com.example.bervageorder.data.entity.BeverageEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType

data class Beverage(
    val menuType: MenuType,
    val name: String,
    val temperature: TemperatureType,
    val price: String,
    val isCaffeine: Boolean
) {
    companion object {
        operator fun invoke(beverageEntity: BeverageEntity): Beverage {
            return Beverage(
                // TODO API Call 시, Entity가 null인 타입은 defaut로 어떻게 처리를 해야하는지
                menuType = beverageEntity.type ?: MenuType.NONE,
                name = beverageEntity.name.orEmpty(),
                temperature = beverageEntity.temperature ?: TemperatureType.NONE,
                price = beverageEntity.price.orEmpty(),
                isCaffeine = beverageEntity.isCaffeine ?: false
            )
        }
    }
}