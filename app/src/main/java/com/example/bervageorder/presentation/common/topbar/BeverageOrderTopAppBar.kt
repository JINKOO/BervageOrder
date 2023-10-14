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
    if (state.titleId == null) return
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