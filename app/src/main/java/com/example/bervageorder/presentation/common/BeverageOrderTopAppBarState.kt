package com.example.bervageorder.presentation.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.bervageorder.R

sealed class BeverageOrderTopAppBarState(@StringRes val titleId: Int?) {
    object IntroTitle : BeverageOrderTopAppBarState(null)
    object MenuListTitle : BeverageOrderTopAppBarState(R.string.title_menu_list_screen)
    object MenuDetailTitle : BeverageOrderTopAppBarState(R.string.title_menu_detail_screen)
    object OrderTitle : BeverageOrderTopAppBarState(R.string.title_order_screen)
}

/**
 *  Composable에서만 사용할 수 있도록 Annotation을 추가
 */
@Composable
fun BeverageOrderTopAppBarState.getTitleId(): Int {
    return this.titleId ?: R.string.empty_string
}
