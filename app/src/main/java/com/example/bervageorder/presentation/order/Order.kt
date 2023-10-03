package com.example.bervageorder.presentation.order

import com.example.bervageorder.domain.model.Menu

object Order {

    data class OrderUiState(
        val menu: Menu? = null,
        val optionList: List<String> = emptyList()
    )
}