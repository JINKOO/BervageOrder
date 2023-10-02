package com.example.bervageorder.presentation.orderdetail

import com.example.bervageorder.domain.model.Menu

object OrderDetail {
    data class OrderDetailUiState(
        val menu: Menu? = null
    )
}