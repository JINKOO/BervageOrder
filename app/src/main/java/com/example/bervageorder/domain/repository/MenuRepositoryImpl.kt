package com.example.bervageorder.domain.repository

import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType
import com.example.bervageorder.domain.model.Menu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor() : MenuRepository {

    override suspend fun getMenuList(): Result<List<Menu>> =
        runCatching { getFakeMenuList().map { Menu(it) } }
            .onSuccess { it }
            .onFailure {
                Timber.w("getMenuList() ERROR :: ${it.message}")
            }

    private suspend fun getFakeMenuList(): List<MenuEntity> =
        withContext(Dispatchers.Default) {
            mutableListOf(
                MenuEntity(
                    type = MenuType.COFFEE,
                    name = "아메리카노",
                    temperature = TemperatureType.BOTH,
                    price = "1000",
                    isCaffeine = true
                ),
                MenuEntity(
                    type = MenuType.COFFEE,
                    name = "카페라떼",
                    temperature = TemperatureType.BOTH,
                    price = "1500",
                    isCaffeine = true
                ),
                MenuEntity(
                    type = MenuType.COFFEE,
                    name = "카푸치노",
                    temperature = TemperatureType.BOTH,
                    price = "2000",
                    isCaffeine = true
                ),
                MenuEntity(
                    type = MenuType.ADE,
                    name = "오렌지에이드",
                    temperature = TemperatureType.ICE,
                    price = "2500",
                    isCaffeine = false
                ),
                MenuEntity(
                    type = MenuType.ADE,
                    name = "망고에이드",
                    temperature = TemperatureType.ICE,
                    price = "2500",
                    isCaffeine = false
                ),
                MenuEntity(
                    type = MenuType.TEA,
                    name = "얼그레이티",
                    temperature = TemperatureType.HOT,
                    price = "1000",
                    isCaffeine = true
                ),
                MenuEntity(
                    type = MenuType.ADE,
                    name = "페퍼민트티",
                    temperature = TemperatureType.HOT,
                    price = "2500",
                    isCaffeine = false
                ),
                MenuEntity(
                    type = MenuType.DESSERT,
                    name = "치즈케이크",
                    temperature = TemperatureType.NONE,
                    price = "3000",
                    isCaffeine = false
                ),
                MenuEntity(
                    type = MenuType.DESSERT,
                    name = "초코케이크",
                    temperature = TemperatureType.NONE,
                    price = "3000",
                    isCaffeine = false
                ),
                MenuEntity(
                    type = MenuType.DESSERT,
                    name = "마들렌",
                    temperature = TemperatureType.NONE,
                    price = "1000",
                    isCaffeine = false
                ),
                MenuEntity(
                    type = MenuType.DESSERT,
                    name = "휘낭시에",
                    temperature = TemperatureType.NONE,
                    price = "1500",
                    isCaffeine = false
                )
            )
        }
}