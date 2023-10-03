package com.example.bervageorder.presentation.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    navigateUp:() -> Unit,
    navigateToIntro: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            BeverageOrderTopAppBar(
                titleId = R.string.titile_order_screen,
                navigateUp = navigateUp
            )
        },
    ) { paddingValues ->
        OrderContent(
            modifier = modifier.padding(paddingValues),
            menu = uiState.menu,
            optionList = uiState.optionList,
            navigateToIntro = {}
        )
    }
}

@Composable
private fun OrderContent(
    modifier: Modifier = Modifier,
    menu: Menu?,
    optionList: List<String>,
    navigateToIntro: () -> Unit
) {
    if (menu != null) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = menu.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = menu.price,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .fillMaxWidth(),
                onClick = { navigateToIntro() }
            ) {
                Text(
                    text = stringResource(R.string.button_next),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}