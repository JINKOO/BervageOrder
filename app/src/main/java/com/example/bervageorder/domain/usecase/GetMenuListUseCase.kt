package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.Menu
import kotlinx.coroutines.flow.Flow

interface GetMenuListUseCase {
    suspend fun getMenuList(): List<Menu>
    fun getMenuListFlow(): Flow<List<Menu>>
}