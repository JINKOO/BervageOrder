package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.MenuRepository
import javax.inject.Inject

class SetOptionListUseCaseImpl @Inject constructor(
    private val repository: MenuRepository
) : SetOptionListUseCase {
    override suspend fun setOptionList(menuId: String, optionList: List<String>): Result<Boolean> {
        return repository.setOptionList(menuId = menuId, optionList = optionList)
    }
}