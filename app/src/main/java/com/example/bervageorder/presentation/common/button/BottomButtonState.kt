package com.example.bervageorder.presentation.common.button

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.bervageorder.R

sealed class BottomButtonState(@StringRes val stringResId: Int?) {
    object Next: BottomButtonState(stringResId = R.string.button_next)
    object Close: BottomButtonState(stringResId = R.string.button_close)
}

@Composable
fun BottomButtonState.getBottomButtonTextId(): Int {
    return this.stringResId ?: R.string.empty_string
}
