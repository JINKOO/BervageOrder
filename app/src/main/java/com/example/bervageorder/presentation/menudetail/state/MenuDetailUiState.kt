package com.example.bervageorder.presentation.menudetail.state

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.bervageorder.R
import com.example.bervageorder.domain.model.Menu

sealed class MenuDetailUiState {
    object None: MenuDetailUiState()

    object Loading: MenuDetailUiState()

    object Error: MenuDetailUiState()

    data class Success(
        val menu: Menu? = null,
        val isShowIceQuantityOption: Boolean = false,
        val isShowMessage: Boolean = false,
        val isNavigateToNext: Boolean = false
    ): MenuDetailUiState()
}

data class MenuOption(
    @StringRes val title: Int,
    @ColorRes val selectedTextColor: Color,
    @ColorRes val unSelectedTextColor: Color,
    @ColorRes val selectedBackGroundColor: Color,
    @ColorRes val unSelectedBackGroundColor: Color
)

val defaultOptionLists = listOf(
    MenuOption(
        title = R.string.button_hot,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    ),
    MenuOption(
        title = R.string.button_ice,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    )
)

val deCaffeineOptionList = listOf(
    MenuOption(
        title = R.string.button_caffeine,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    ),
    MenuOption(
        title = R.string.button_decaffeine,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    )
)

val iceQuantityOptionList = listOf(
    MenuOption(
        title = R.string.button_ice_small,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    ),
    MenuOption(
        title = R.string.button_ice_medium,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    ),
    MenuOption(
        title = R.string.button_ice_large,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    )
)

