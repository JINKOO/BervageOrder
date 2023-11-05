package com.example.bervageorder.data.repository

import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.model.OrderMenuOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MenuRepository {
    val menuListFlow: Flow<List<Menu>>
    val menuList: List<Menu>
    val orderMenuFlow: StateFlow<OrderMenuOption>

    suspend fun getMenuList(): Result<List<Menu>>
    suspend fun getMenuById(menuId: String): Result<Menu?>
    suspend fun postOptionList(orderMenuOption: OrderMenuOption): Result<Unit>
    suspend fun clearAll()
}