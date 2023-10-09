package com.example.bervageorder.presentation.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.bervageorder.R

sealed class BeverageOrderTopAppBarState(@StringRes open val titleId: Int?) {
    object MenuDetail : BeverageOrderTopAppBarState(R.string.title_order_screen)

    object MenuList : BeverageOrderTopAppBarState(R.string.title_menu_list_screen)

    object Intro : BeverageOrderTopAppBarState(null)

    object Order : BeverageOrderTopAppBarState(R.string.title_order_screen)
}

@Composable
fun BeverageOrderTopAppBarState.getTitleId(): Int {
    return this.titleId ?: R.string.empty
}
