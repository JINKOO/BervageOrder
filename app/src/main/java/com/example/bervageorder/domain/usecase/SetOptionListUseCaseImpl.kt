package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.OptionType
import javax.inject.Inject

class SetOptionListUseCaseImpl @Inject constructor(
    private val repository: MenuRepository
) : SetOptionListUseCase {
    override suspend fun setOptionList(menuId: String, optionList: List<OptionType>): Result<Boolean> {
        return repository.setOptionList(menuId = menuId, optionList = optionList)
    }
}