package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.OrderMenuOption
import javax.inject.Inject

class PostOptionListUseCaseImpl @Inject constructor(
    private val repository: MenuRepository
) : PostOptionListUseCase {
    override suspend fun postOptionList(orderMenuOption: OrderMenuOption): Result<Unit> {
        return repository.postOptionList(orderMenuOption)
    }
}