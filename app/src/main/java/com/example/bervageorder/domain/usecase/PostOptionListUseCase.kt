package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.OrderMenuOption

interface PostOptionListUseCase {
    suspend fun postOptionList(orderMenuOption: OrderMenuOption): Result<Unit>
}