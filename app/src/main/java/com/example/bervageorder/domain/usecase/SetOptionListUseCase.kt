package com.example.bervageorder.domain.usecase

import com.example.bervageorder.domain.model.OptionType

interface SetOptionListUseCase {
    suspend fun setOptionList(menuId: String, optionList: List<OptionType>): Result<Boolean>
}