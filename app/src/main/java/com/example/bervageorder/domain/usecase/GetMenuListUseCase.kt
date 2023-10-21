package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.Menu
import kotlinx.coroutines.flow.Flow

interface GetMenuListUseCase {
    suspend fun getMenuList(): Result<List<Menu>>
    suspend fun getMenuListFlow(): Flow<List<Menu>>
}