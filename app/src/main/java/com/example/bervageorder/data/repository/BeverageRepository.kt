package com.example.bervageorder.data.repository

import com.example.bervageorder.domain.model.Beverage

interface BeverageRepository {
    suspend fun getMenuList(): Result<List<Beverage>>
}