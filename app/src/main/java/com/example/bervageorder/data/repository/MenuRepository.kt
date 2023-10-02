package com.example.bervageorder.data.repository

import com.example.bervageorder.domain.model.Menu

interface MenuRepository {
    val menuList: List<Menu>
    suspend fun getMenuList(): Result<List<Menu>>
    suspend fun getMenuById(menuId: String): Result<Menu>
}