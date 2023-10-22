package com.example.bervageorder.presentation.menudetail.state

import androidx.annotation.StringRes
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.presentation.common.error.ErrorState

sealed class MenuDetailUiState {
    object None: MenuDetailUiState()

    object Loading: MenuDetailUiState()

    data class Success(
        val menu: Menu? = null
    ): MenuDetailUiState()

    data class Error(
        @StringRes val messageId: Int,
    ): MenuDetailUiState(), ErrorState {
        override val errorMessageId: Int
            get() = messageId
    }

    object AllOptionSelected: MenuDetailUiState()
}