package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.BeverageRepository
import com.example.bervageorder.domain.model.Beverage
import javax.inject.Inject

class GetMenuListUseCaseImpl @Inject constructor(
    private val beverageRepository: BeverageRepository
) : GetMenuListUseCase {

    override suspend fun getMenuList(): Result<List<Beverage>> {
        return beverageRepository.getMenuList()
    }
}