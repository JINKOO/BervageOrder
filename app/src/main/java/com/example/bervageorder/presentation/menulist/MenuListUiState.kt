package com.example.bervageorder.presentation.menulist

import androidx.annotation.StringRes
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.presentation.common.error.ErrorState

sealed class MenuListUiState {
    object None : MenuListUiState()

    object Loading : MenuListUiState()

    // TODO : JA-12314 : 2021/11/15
    data class Error(
        @StringRes val errorMessage: Int,
        val dialog: () -> Unit,
    ) : MenuListUiState(), ErrorState {
        override val errorMessageId: Int
            get() = errorMessage
    }

    data class Success(
        val menuListSubUiState: MenuListSubUiState,
    ) : MenuListUiState()
}

sealed class MenuListSubUiState {
    data class Success(
        val menuMap: Map<MenuType, List<Menu>> = emptyMap(),
    ) : MenuListSubUiState()

    data class Error(
        @StringRes val errorMessage: Int,
    ) : MenuListSubUiState(), ErrorState {
        override val errorMessageId: Int
            get() = errorMessage
    }
}
