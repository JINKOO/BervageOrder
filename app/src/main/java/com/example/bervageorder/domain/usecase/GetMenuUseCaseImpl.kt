package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.Menu
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetMenuUseCaseImpl @Inject constructor(
    private val menuRepository: MenuRepository
) : GetMenuUseCase {
    override suspend fun getMenuById(menuId: String): Result<Menu?> {
        return menuRepository.getMenuById(menuId = menuId)
    }
}