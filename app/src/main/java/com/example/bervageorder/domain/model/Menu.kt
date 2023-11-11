package com.example.bervageorder.domain.model

import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType
import java.text.DecimalFormat

data class Menu(
    // id는 기본값을 주면 안된다.
    val id: String,
    val type: MenuType = MenuType.NONE, // 메뉴에 type이 정말 필요한지 파악 해보기
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

    companion object {
        operator fun invoke(menuEntity: MenuEntity): Menu {
            return Menu(
                // TODO 2회차 질문 :: API Call 시, Entity가 null인 타입은 defaut로 어떻게 처리를 해야하는지
                // -> menu를 Null로 처리해야한다. id가 널이면 instance를 만들면 안된다. 예외를 던지던가
                // OOP의 기본값이 없으면 아예 생성하면 안된다.
                // data layer에서는 방어코드가 없어도 되지만, domain에서 방어코드
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

// 얘는 도메인에 있어도 됨
fun Int.toDecimalFormat(): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(this)
}

/**
 * Value Class
 *
 * 단위가 안맞는 값들을 기준으로 통일할때
 * 원 가치를 통일하고 싶을때
 */
