package com.example.bervageorder.presentation.menulist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.bervageorder.domain.model.Beverage
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuListMainScreen(
    modifier: Modifier = Modifier,
    viewModel: MenuListViewModel,
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BeverageOrderTopAppBar(
                titleId = R.string.title_menu_list_screen,
                navigateUp = { navigateUp() }
            )
        },
    ) { paddingValues ->
        MenuList(
            modifier = Modifier.padding(paddingValues = paddingValues),
            menuList = uiState.menuList
        )
    }
}

@Composable
private fun MenuList(
    modifier: Modifier = Modifier,
    menuList: List<Beverage> = emptyList()
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(menuList) { beverage ->
            MenuItem(
                beverage = beverage
            )
        }
    }
}

@Composable
private fun MenuItem(
    modifier: Modifier = Modifier,
    beverage: Beverage
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = beverage.name,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = beverage.price,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
