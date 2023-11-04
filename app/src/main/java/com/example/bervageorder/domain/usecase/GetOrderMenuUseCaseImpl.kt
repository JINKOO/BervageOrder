package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.OrderMenuOption
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderMenuUseCaseImpl @Inject constructor(
    private val menuRepository: MenuRepository
) : GetOrderMenuUseCase {

    // TODO 3회차 질문 :: Usecase에서 ViewModel로 전달할 때, 여기서 Collect 후, 실제 값만 전달하는게 맞는지,
    // or UseCase에서도 Flow로 return해서 ViewModel에서 처리하는 것이 맞는지.
    override suspend fun getOrderMenu(menuId: String): Flow<OrderMenuOption> {
        return menuRepository.orderMenuFlow
    }
}