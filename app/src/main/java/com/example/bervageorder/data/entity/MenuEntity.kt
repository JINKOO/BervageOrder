package com.example.bervageorder.data.entity

import androidx.compose.runtime.Composable

/**
 *  [2회차 수업]
 *  - Modeling 할 때, Boolean은 가급적 쓰지 마라.
 *  정말로 true flase? optional까지 걸어버리면 진짜 헷갈림 의미가 어렵다.
 *
 *  Modeling을 할때에는 Boolean으로 하면 복잡해진다.
 *  --> enum class로 정의
 *
 *  Entity에서 보통 API 응답값을 사용하는데, 다른 타입은 nullable이어도 되지만,
 *  id와 같은 중요한 속성값은 id가 null이면 안된다.
 *  - 서버에서 잘못내려준 경우이다. -> 서버쪽에 수정 요청을 해야한다.
 *  - 서버에서 조차 수정이 어렵다면 default로 UUID를 생성한다.
 */

/**
 *  Menu / Order 나눌 것 Model
 *
 *  서버에서 받아서 보여지는 것 VO,
 *  서버로 보내는 것은 DTO
 *  화면 전달시에도 DTO 사용자부터 입력 받아서 뭔가 전달할때
 *
 *  각자의 입장이 있기 때문에, 2가지로 Modeling을 한다.
 *
 *  Model이 많아도 된다.
 *  Model을 만드는 것을 겁을 내지 마라
 *  Boolean, Null값은 웬만하면 쓰지 말고 enum으로 관리
 *
 *  최대한 Model에서 모든 것을 끝낸다. enum class, data class는 class이다.
 *  함수를 만들어, UI에서 필요한 형식에 맞게 커스텀이 가능한 함수를 만든다.
 */
data class MenuEntity(
    val id: String? = null,
    val type: MenuType? = null, // 진짜 필요한가..??
    val name: String? = null,
    val temperature: TemperatureType? = null,
    val price: Int? = null,
    val isCaffeine: Boolean? = null
)

enum class MenuType {
    COFFEE,
    ADE,
    TEA,
    DESSERT,
    NONE
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
    NONE
}