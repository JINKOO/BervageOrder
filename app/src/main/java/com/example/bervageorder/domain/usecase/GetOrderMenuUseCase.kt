package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.OrderMenu

interface GetOrderMenuUseCase {
    suspend fun getOrderMenu(menuId: String): Result<OrderMenu>
}