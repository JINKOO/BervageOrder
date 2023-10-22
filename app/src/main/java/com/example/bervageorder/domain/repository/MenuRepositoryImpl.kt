package com.example.bervageorder.domain.repository

import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType
import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.model.OptionType
import com.example.bervageorder.domain.model.OrderMenu
import com.example.bervageorder.presentation.menulist.MenuList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.NullPointerException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepositoryImpl @Inject constructor() : MenuRepository {

    // TODO Flow를 사용하지 않을 때.
    override val menuList: MutableList<Menu> = mutableListOf()
    override val orderMenuOptionList: MutableList<OptionType> = mutableListOf()

    // TODO 2회차 질문 :: menuList에 대한 것을 Flow로 전달할 때, Flow<Result<List<Menu>>>로 전달해야하는지? 아니면, Collect하는 쪽에서
    //  Catch 중간 연산자를 사용해서 Error인 경우를 처리해야하는지
    override val menuListFlow: Flow<List<Menu>> = flow {
        val menuList = getFakeMenuList().map { Menu(it) }
        emit(menuList)
    }

    // TODO List<Menu>를 Flow로 사용하지 않는 경우
    override suspend fun getMenuList(): Result<List<Menu>> =
        // TODO 2회차 질문 :: Repository에서 Entity -> Model로 변경 방식이 맞는지? runCatching의 올바르게 사용했는지..??
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
        // TODO 2회차 질문 :: find는 해당 조건에 없다면 null을 반환하는데, 이때, Null 처리를 어떻게 하면 되는지? Null인 경우, 빈 객체로 정의?
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

    override suspend fun setOptionList(menuId: String, optionList: List<OptionType>): Result<Boolean> {
        return try {
            postOptionList(optionList)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun postOptionList(optionList: List<OptionType>) {
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

    override suspend fun clearAll() = withContext(Dispatchers.IO) {
        menuList.clear()
        orderMenuOptionList.clear().also {
            Timber.d("clearAll() :: ${it}")
        }
    }

    private fun createMenuId(): String = UUID.randomUUID().toString()
}