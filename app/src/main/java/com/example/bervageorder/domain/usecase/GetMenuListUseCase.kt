package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.Menu

interface GetMenuListUseCase {
    suspend fun getMenuList(): Result<List<Menu>>
}