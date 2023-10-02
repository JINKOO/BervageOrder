package com.example.bervageorder.presentation.menulist

import com.example.bervageorder.domain.model.Menu

object MenuList {

    // route( navigate between Screen )
    interface Router {

    }

    // UiState
    data class MenuListUiState(
        // state
        val isLoading: Boolean = false,

        // data
        val menuList: List<Menu> = emptyList()
    )

    // UiEvent (Compose -> ViewModel)
    interface UiEvent {

    }
}