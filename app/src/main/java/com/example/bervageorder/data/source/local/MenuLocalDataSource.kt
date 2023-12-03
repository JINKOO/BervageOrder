package com.example.bervageorder.data.source.local

import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType
import com.example.bervageorder.domain.model.Menu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

// TODO menuList를 flow를 사용해서 한다.
class MenuLocalDataSource @Inject constructor() {

    /**
     *  Data Stream을 사용하는 목적
     *  1. 언어적인 특성 :: 코틀린은 return값은 1개, 이를 해결하려고, return을 시간에 따라서 여러번하려고 floW에서 여러가지 flow (Emit)
     *  2. data stream :: data가 계속 흘러간다. reactive Prgramming
     */
    val menuList: Flow<List<Menu>> = flow {
        val menuList = getFakeMenuList().map { Menu(it) }
        emit(menuList)
    }

    /**
     *  여기서 Flow 자체로
     */
    private suspend fun getFakeMenuList(): List<MenuEntity> =
        withContext(Dispatchers.Default) {
            mutableListOf(
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.COFFEE,
                    name = "아메리카노",
                    temperature = TemperatureType.BOTH,
                    price = 1_000,
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.COFFEE,
                    name = "카페라떼",
                    temperature = TemperatureType.BOTH,
                    price = 1_500,
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.COFFEE,
                    name = "카푸치노",
                    temperature = TemperatureType.BOTH,
                    price = 2_000,
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.ADE,
                    name = "오렌지에이드",
                    temperature = TemperatureType.ICE,
                    price = 2_500,
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.ADE,
                    name = "망고에이드",
                    temperature = TemperatureType.ICE,
                    price = 2_500,
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.TEA,
                    name = "얼그레이티",
                    temperature = TemperatureType.HOT,
                    price = 1_000,
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.TEA,
                    name = "페퍼민트티",
                    temperature = TemperatureType.HOT,
                    price = 2_500,
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "치즈케이크",
                    temperature = TemperatureType.NONE,
                    price = 3_000,
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "초코케이크",
                    temperature = TemperatureType.NONE,
                    price = 3_000,
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "마들렌",
                    temperature = TemperatureType.NONE,
                    price = 1_000,
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "휘낭시에",
                    temperature = TemperatureType.NONE,
                    price = 1_500,
                    isCaffeine = false
                )
            )
        }

    private fun createMenuId(): String = UUID.randomUUID().toString()
}