package com.example.bervageorder.presentation.intro.state

import androidx.annotation.StringRes

sealed class IntroUiState {
    object None: IntroUiState()

    object Loading: IntroUiState()

    object Error: IntroUiState()

    data class Success(
        @StringRes val introTitleId: Int
    ): IntroUiState()
}