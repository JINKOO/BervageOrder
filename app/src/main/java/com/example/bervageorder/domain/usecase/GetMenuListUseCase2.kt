package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.MenuRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetMenuListUseCase2 @Inject constructor(
    private val menuRepository: MenuRepository,
) {
    suspend fun invoke() = menuRepository.getMenuList()
}
