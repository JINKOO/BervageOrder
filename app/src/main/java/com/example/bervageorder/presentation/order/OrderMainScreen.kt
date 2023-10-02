package com.example.bervageorder.presentation.order

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bervageorder.R
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderMainScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            BeverageOrderTopAppBar(
                titleId = R.string.title_order_screen,
                navigateUp = navigateUp
            )
        },
    ) { paddingValues ->
        OrderContent(
            modifier = modifier.padding(paddingValues = paddingValues)
        )
    }
}

@Composable
private fun OrderContent(
    modifier: Modifier = Modifier,
) {

}