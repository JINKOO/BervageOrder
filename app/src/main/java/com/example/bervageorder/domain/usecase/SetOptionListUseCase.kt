package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.OrderMenuOption

interface SetOptionListUseCase {
    suspend fun postOptionList(orderMenuOption: OrderMenuOption): Result<Unit>
}