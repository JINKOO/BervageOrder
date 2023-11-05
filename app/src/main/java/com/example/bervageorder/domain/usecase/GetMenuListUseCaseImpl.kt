package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.Menu
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetMenuListUseCaseImpl @Inject constructor(
    private val menuRepository: MenuRepository,
) : GetMenuListUseCase {
    override suspend fun getMenuList(): Result<List<Menu>> {
        return menuRepository.getMenuList()
    }

    override fun getMenuListFlow(): Flow<List<Menu>> {
        return menuRepository.menuListFlow
    }
}
