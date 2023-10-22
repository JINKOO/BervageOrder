package com.example.bervageorder.presentation.menulist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bervageorder.R
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.presentation.common.error.ErrorScreen
import com.example.bervageorder.presentation.menulist.state.MenuListSubUiState
import com.example.bervageorder.presentation.menulist.state.MenuListUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuList(
    modifier: Modifier = Modifier,
    uiState: MenuListUiState.Success,
    navigateToOrderDetail: (String) -> Unit
) {
    when (uiState.menuListSubUiState) {
        is MenuListSubUiState.Success -> {
            LazyColumn(
                modifier = modifier
            ) {
                uiState.menuListSubUiState.menuMap.forEach { (menuType, menuListByMenuType) ->
                    stickyHeader {
                        MenuHeader(
                            menuType = menuType,
                            color = Color.LightGray
                        )
                    }

                    items(menuListByMenuType) { menu ->
                        MenuItem(
                            menu = menu,
                            navigateToOrderDetail =  { navigateToOrderDetail(menu.id) }
                        )
                    }
                }
            }
        }
        is MenuListSubUiState.Error -> {
            ErrorScreen(errorState = uiState.menuListSubUiState)
        }
    }
}


@Composable
private fun MenuHeader(
    menuType: MenuType,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
    ) {
        Text(
            /**
             *  tablet을 지원할 때, dp 값은 어떻게 관리하는지?
             */
            modifier = Modifier.padding(horizontal = 16.dp),
            text = menuType.name,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
private fun MenuItem(
    modifier: Modifier = Modifier,
    menu: Menu,
    navigateToOrderDetail: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { navigateToOrderDetail() }
    ) {
        Text(
            text = menu.name,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = menu.priceFormatString,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}