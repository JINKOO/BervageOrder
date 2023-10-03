package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.OrderMenu
import javax.inject.Inject

class GetOrderMenuUseCaseImpl @Inject constructor(
    private val menuRepository: MenuRepository
) : GetOrderMenuUseCase {

    override suspend fun getOrderMenu(menuId: String): Result<OrderMenu> {
        return menuRepository.getOrderMenu(menuId = menuId)
    }
}