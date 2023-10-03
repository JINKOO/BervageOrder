package com.example.bervageorder.domain.usecase

interface SetOptionListUseCase {
    suspend fun setOptionList(menuId: String, optionList: List<String>)
}