package com.example.bervageorder.presentation.order.state

import androidx.annotation.StringRes
import com.example.bervageorder.domain.model.Menu

sealed class OrderUiState {
    object None: OrderUiState()

    object Loading: OrderUiState()

    data class Success(
        val menu: Menu? = null,
        val optionListString: String = ""
    ): OrderUiState()

    data class Error(
        @StringRes val errorMessage: Int
    ) : OrderUiState()
}
