package com.example.bervageorder.data.repository

import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.model.OptionType
import com.example.bervageorder.domain.model.OrderMenu
import com.example.bervageorder.domain.model.OrderMenuOption
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    val menuListFlow: Flow<List<Menu>>
    val menuList: List<Menu>
    val orderMenuOptionList: List<OptionType>
    val menuOption: OrderMenuOption

    suspend fun getMenuList(): Result<List<Menu>>
    suspend fun getMenuById(menuId: String): Result<Menu?>
    suspend fun postOptionList(orderMenuOption: OrderMenuOption): Result<Unit>
    suspend fun getOrderMenu(menuId: String): Result<OrderMenu>
    suspend fun clearAll()
}