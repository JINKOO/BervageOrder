package com.example.bervageorder.presentation.menudetail

import com.example.bervageorder.domain.model.Menu

object MenuDetail {
    data class OrderDetailUiState(
        val menu: Menu? = null,
        val isShowIceQuantityOption: Boolean = false,
        val isShowMessage: Boolean = false
    )
}