package com.example.bervageorder.presentation.menulist

import com.example.bervageorder.data.entity.MenuType
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
        val menuMap: Map<MenuType, List<Menu>> = emptyMap()
    )

    // UiEvent (Compose -> ViewModel)
    interface UiEvent {

    }
}