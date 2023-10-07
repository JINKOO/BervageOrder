package com.example.bervageorder.domain.repository

import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType
import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.model.OrderMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.NullPointerException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepositoryImpl @Inject constructor() : MenuRepository {

    override val menuList: MutableList<Menu> = mutableListOf()

    override val orderMenuOptionList: MutableList<String> = mutableListOf()

    override suspend fun getMenuList(): Result<List<Menu>> =
        // TODO 질문 :: Repository에서 Entity -> Model로 변경 방식이 맞는지? runCatching의 올바르게 사용했는지..??
        runCatching {
            Timber.d("getMenuList() START :: ${menuList.size}")
            getFakeMenuList().map { Menu(it) }.also {
                Timber.d("getMenuList() END :: ${it.size}")
            }
        }.onSuccess {
            Timber.d("getMenuList() SUCCESS :: ${menuList.size}")
            it.forEach { menu -> menuList.add(menu) }.also {
                Timber.d("getMenuList() END :: ${menuList.size} / ${menuList}")
            }
        }.onFailure {
            Timber.w("getMenuList() ERROR :: ${it.message}")
        }

    override suspend fun getMenuById(menuId: String): Result<Menu?> = runCatching {
        Timber.d("getMenuById() menuList :: ${menuList}")
        // TODO 질문 :: find는 해당 조건에 없다면 null을 반환하는데, 이때, Null 처리를 어떻게 하면 되는지? Null인 경우, 빈 객체로 정의?
        menuList.find { it.id == menuId } // ?: Menu()
    }.onSuccess {
        Timber.d("getMenuById() SUCCESS :: ${it}")
    }.onFailure {
        Timber.w("getMenuById() ERROR :: ${it.message}")
    }

    private suspend fun getFakeMenuList(): List<MenuEntity> =
        withContext(Dispatchers.Default) {
            mutableListOf(
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.COFFEE,
                    name = "아메리카노",
                    temperature = TemperatureType.BOTH,
                    price = "1000",
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.COFFEE,
                    name = "카페라떼",
                    temperature = TemperatureType.BOTH,
                    price = "1500",
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.COFFEE,
                    name = "카푸치노",
                    temperature = TemperatureType.BOTH,
                    price = "2000",
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.ADE,
                    name = "오렌지에이드",
                    temperature = TemperatureType.ICE,
                    price = "2500",
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.ADE,
                    name = "망고에이드",
                    temperature = TemperatureType.ICE,
                    price = "2500",
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.TEA,
                    name = "얼그레이티",
                    temperature = TemperatureType.HOT,
                    price = "1000",
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.TEA,
                    name = "페퍼민트티",
                    temperature = TemperatureType.HOT,
                    price = "2500",
                    isCaffeine = true
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "치즈케이크",
                    temperature = TemperatureType.NONE,
                    price = "3000",
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "초코케이크",
                    temperature = TemperatureType.NONE,
                    price = "3000",
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "마들렌",
                    temperature = TemperatureType.NONE,
                    price = "1000",
                    isCaffeine = false
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "휘낭시에",
                    temperature = TemperatureType.NONE,
                    price = "1500",
                    isCaffeine = false
                )
            )
        }

    override suspend fun setOptionList(menuId: String, optionList: List<String>): Result<Boolean> {
        return try {
            postOptionList(optionList)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun postOptionList(optionList: List<String>) {
        withContext(Dispatchers.IO) {
            optionList.forEach {
                orderMenuOptionList.add(it)
            }.also {
                Timber.i("setOptionList() :: ${orderMenuOptionList.size}")
            }
        }
    }

    // TODO 질문 :: runCatching 올바른 사용법
    override suspend fun getOrderMenu(menuId: String): Result<OrderMenu> = runCatching {
        val menu =
            getMenuById(menuId = menuId).getOrNull() ?: throw NullPointerException("Menu Is Null!!")
        OrderMenu(menu = menu, optionList = orderMenuOptionList)
    }.onSuccess {
        Timber.i("getOrderMenu() Success :: ${it} ")
        Result.success(it)
    }.onFailure {
        Timber.w("getOrderMenu() ERROR :: ${it.message}")
        Result.failure<Exception>(it)
    }

    private fun createMenuId(): String = UUID.randomUUID().toString()
}