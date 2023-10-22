package com.example.bervageorder.data.repository

import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.model.OptionType
import com.example.bervageorder.domain.model.OrderMenu
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    val menuListFlow: Flow<List<Menu>>
    val menuList: List<Menu>
    val orderMenuOptionList: List<OptionType>
    suspend fun getMenuList(): List<Menu>
    suspend fun getMenuById(menuId: String): Result<Menu?>
    suspend fun setOptionList(menuId: String, optionList: List<OptionType>): Result<Boolean>
    suspend fun getOrderMenu(menuId: String): Result<OrderMenu>
    suspend fun clearAll()
}