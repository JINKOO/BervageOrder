package com.example.bervageorder.presentation.intro

import androidx.annotation.StringRes
import com.example.bervageorder.R
import com.example.bervageorder.presentation.common.error.ErrorState

sealed class IntroUiState {
    object None: IntroUiState()

    object Loading: IntroUiState()

    data class Success(
        @StringRes val introTitleId: Int
    ): IntroUiState()

    data class Error(
        @StringRes val errorMsgId: Int
    ): IntroUiState(), ErrorState {
        override val errorMessageId: Int
            get() = R.string.title_error_message
    }
}