package com.example.bervageorder.data.repository

import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.model.OrderMenu

interface MenuRepository {
    val menuList: List<Menu>
    val orderMenuOptionList: List<String>
    suspend fun getMenuList(): Result<List<Menu>>
    suspend fun getMenuById(menuId: String): Result<Menu?>
    suspend fun setOptionList(menuId: String, optionList: List<String>): Result<Boolean>
    suspend fun getOrderMenu(menuId: String): Result<OrderMenu>
    suspend fun clearAll()
}