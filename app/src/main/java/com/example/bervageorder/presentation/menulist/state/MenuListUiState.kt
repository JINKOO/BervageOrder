package com.example.bervageorder.presentation.menulist.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.presentation.common.error.ErrorState

sealed class MenuListUiState {
    object None : MenuListUiState()

    object Loading : MenuListUiState()

    data class Error(
        @StringRes val errorMessage: Int,
    ) : MenuListUiState(), ErrorState {
        override val errorMessageId: Int
            get() = errorMessage
    }

    data class Success(
        val menuMap: Map<MenuType, List<Menu>> = emptyMap(),
    ) : MenuListUiState()
}

@Composable
fun MenuListUiState.Success.toLoading() = menuMap.isEmpty()
