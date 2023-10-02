package com.example.bervageorder.data.repository

import com.example.bervageorder.domain.model.Menu

interface MenuRepository {
    suspend fun getMenuList(): Result<List<Menu>>
}