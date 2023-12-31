package com.example.bervageorder.presentation.common.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeverageOrderTopAppBar(
    modifier: Modifier = Modifier,
    state: BeverageOrderTopAppBarState,
    navigateUp: () -> Unit
) {
    // 비즈니스 모델을 State에서 로직 isDisplay()
    // 안그리겠다는 것도 그리는 것. (조기 탈출) 판단은 isDisplay
    if (state.isDisplay()) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(state.getTitleId())
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }
}