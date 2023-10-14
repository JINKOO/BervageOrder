package com.example.bervageorder.presentation.menulist

import androidx.annotation.StringRes
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.domain.model.Menu

sealed class MenuListUiState {
    object None : MenuListUiState()

    object Loading : MenuListUiState()

    data class Error(
        @StringRes val errorMessage: Int
    ) : MenuListUiState()

    data class Success(
        val menuMap: Map<MenuType, List<Menu>> = emptyMap()
    ) : MenuListUiState()
}