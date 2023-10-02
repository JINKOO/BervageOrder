package com.example.bervageorder.presentation.orderdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bervageorder.R
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderMainScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel,
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            BeverageOrderTopAppBar(
                titleId = R.string.title_order_screen,
                navigateUp = navigateUp
            )
        },
    ) { paddingValues ->
        OrderContent(
            modifier = modifier.padding(paddingValues = paddingValues),
            menu = uiState.menu
        )
    }
}

@Composable
private fun OrderContent(
    modifier: Modifier = Modifier,
    menu: Menu?
) {
    if (menu != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 선택한 메뉴 명
            HeaderTitle(
                modifier = Modifier.fillMaxWidth(),
                name = menu.name,
                price = menu.price
            )
            // 기본 옵션 ICE / HOT

            // 디카페인 선택

            // 얼음
        }
    }
}

@Composable
private fun HeaderTitle(
    modifier: Modifier = Modifier,
    name: String,
    price: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = price,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}