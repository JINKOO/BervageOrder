package com.example.bervageorder.presentation.menulist

import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBarState

object MenuList {
    // UiState

    // UiEvent (Compose -> ViewModel)
    interface UiEvent
}

sealed class MenuListUiState {
    object None : MenuListUiState()
    object Loading : MenuListUiState()

    object Error : MenuListUiState()

    data class Success(
        val menuMap: Map<MenuType, List<Menu>>,
    ) : MenuListUiState() {
        companion object {
            operator fun invoke(menuList: List<Menu>): Success {
                val groupedMenuMap = menuList.groupBy { it.menuType }
                return Success(menuMap = groupedMenuMap)
            }
        }
    }

    companion object {
        operator fun invoke(result: Result<List<Menu>>): MenuListUiState {
            when {
                result.isSuccess -> {
                    return Success(menuList = result.getOrNull() ?: emptyList())
                }

                result.isFailure -> {
                    return Error
                }
            }
        }
    }
}

fun MenuListUiState.getAppBarState(): BeverageOrderTopAppBarState {
    return BeverageOrderTopAppBarState.MenuList
}
