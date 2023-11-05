package com.example.bervageorder.domain.repository

import com.example.bervageorder.data.entity.MenuEntity
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.data.entity.TemperatureType
import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.model.OrderMenuOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepositoryImpl @Inject constructor() : MenuRepository {

    // TODO 2회차 질문 :: menuList에 대한 것을 Flow로 전달할 때, Flow<Result<List<Menu>>>로 전달해야하는지? 아니면, Collect하는 쪽에서
    //  Catch 중간 연산자를 사용해서 Error인 경우를 처리해야하는지
    // 답변 : 스타일에 맞게 처리하면 됨
    override val menuListFlow: Flow<List<Menu>> = flow {
        val menuList = getFakeMenuList().map { Menu(it) }
        emit(menuList)
    }

    override val menuList: MutableList<Menu> = mutableListOf()

    private val _orderMenuFlow: MutableStateFlow<OrderMenuOption> =
        MutableStateFlow(OrderMenuOption())
    override val orderMenuFlow = _orderMenuFlow.asStateFlow()

    // TODO 2회차 질문 :: Repository에서 Entity -> Model로 변경 방식이 맞는지? runCatching의 올바르게 사용했는지..??
    private suspend fun getFakeMenuList(): List<MenuEntity> =
        withContext(Dispatchers.Default) {
            mutableListOf(
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.COFFEE,
                    name = "아메리카노",
                    temperature = TemperatureType.BOTH,
                    price = 1_000,
                    isCaffeine = true,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.COFFEE,
                    name = "카페라떼",
                    temperature = TemperatureType.BOTH,
                    price = 1_500,
                    isCaffeine = true,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.COFFEE,
                    name = "카푸치노",
                    temperature = TemperatureType.BOTH,
                    price = 2_000,
                    isCaffeine = true,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.ADE,
                    name = "오렌지에이드",
                    temperature = TemperatureType.ICE,
                    price = 2_500,
                    isCaffeine = false,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.ADE,
                    name = "망고에이드",
                    temperature = TemperatureType.ICE,
                    price = 2_500,
                    isCaffeine = false,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.TEA,
                    name = "얼그레이티",
                    temperature = TemperatureType.HOT,
                    price = 1_000,
                    isCaffeine = true,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.TEA,
                    name = "페퍼민트티",
                    temperature = TemperatureType.HOT,
                    price = 2_500,
                    isCaffeine = true,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "치즈케이크",
                    temperature = TemperatureType.NONE,
                    price = 3_000,
                    isCaffeine = false,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "초코케이크",
                    temperature = TemperatureType.NONE,
                    price = 3_000,
                    isCaffeine = false,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "마들렌",
                    temperature = TemperatureType.NONE,
                    price = 1_000,
                    isCaffeine = false,
                ),
                MenuEntity(
                    id = createMenuId(),
                    type = MenuType.DESSERT,
                    name = "휘낭시에",
                    temperature = TemperatureType.NONE,
                    price = 1_500,
                    isCaffeine = false,
                ),
            )
        }

    /**
     *  답변 : runCatching은 Kotlin에서 try-catch문을 Result라는 타입을 이용해서 간단히 사용하기 위함.
     *  onSuccess{}, onFailure{}는 1번씩 모두 실행됨, 개념 상 result에따라 어느쪽이 1번만 실행되는 것임.
     *  onMap, fold()를 사용한다.
     *  또한, getOrNull()이렇게 사용하는 것보다는 ViewModel까지 Result로 전달한다.
     *  아니면 중간에 map을 사용하면 됨
     *  map은 성공시에만 수행되기 때문.
     */
    override suspend fun getMenuList(): Result<List<Menu>> = runCatching {
        getFakeMenuList().map { Menu(it) }
    }.onSuccess {
        Timber.d("getMenuList() SUCCESS :: ${menuList.size}")
        it.forEach { menu -> menuList.add(menu) }
    }.onFailure {
        Timber.w("getMenuList() ERROR :: ${it.message}")
    }

    override suspend fun getMenuById(menuId: String): Result<Menu?> = runCatching {
        Timber.d("getMenuById() menuList :: $menuList")
        // TODO 2회차 질문 :: find는 해당 조건에 없다면 null을 반환하는데, 이때, Null 처리를 어떻게 하면 되는지? Null인 경우, 빈 객체로 정의?
        // 답변 : 이 경우에는 null 반환하는 것이 맞다. 조건에 있는 값이 없다는 의미이니깐
        menuList.find { it.id == menuId }
    }.onSuccess {
        Timber.d("getMenuById() SUCCESS :: $it")
    }.onFailure {
        Timber.w("getMenuById() ERROR :: ${it.message}")
    }

    override suspend fun postOptionList(orderMenuOption: OrderMenuOption) = kotlin.runCatching {
        _orderMenuFlow.update {
            it.copy(
                id = orderMenuOption.id,
                name = orderMenuOption.name,
                price = orderMenuOption.price,
                temperature = orderMenuOption.temperature,
                caffeine = orderMenuOption.caffeine,
                iceQuantity = orderMenuOption.iceQuantity,
            )
        }
    }.onSuccess {
        Result.success(Unit)
    }.onFailure {
        Result.failure<Exception>(it)
    }

    override suspend fun clearAll() = withContext(Dispatchers.IO) {
        menuList.clear()
    }

    private fun createMenuId(): String = UUID.randomUUID().toString()
}
