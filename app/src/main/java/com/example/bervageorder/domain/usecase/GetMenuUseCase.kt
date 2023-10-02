package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.Menu

interface GetMenuUseCase {
    suspend fun getMenuById(menuId: String): Result<Menu>
}