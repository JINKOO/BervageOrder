package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.OrderMenuOption
import kotlinx.coroutines.flow.Flow

interface GetOrderMenuUseCase {
    suspend fun getOrderMenu(menuId: String): Flow<OrderMenuOption>
}