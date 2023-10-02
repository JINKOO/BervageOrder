package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.Beverage

interface GetMenuListUseCase {
    suspend fun getMenuList(): Result<List<Beverage>>
}