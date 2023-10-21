package com.example.bervageorder.presentation.menudetail.state

import com.example.bervageorder.domain.model.Menu

sealed class MenuDetailUiState {
    object None: MenuDetailUiState()

    object Loading: MenuDetailUiState()

    data class Success(
        val menu: Menu? = null
    ): MenuDetailUiState()

    data class Error(
        val messageId: Int,
    ): MenuDetailUiState()

    object AllOptionSelected: MenuDetailUiState()
}