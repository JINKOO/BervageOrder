package com.example.bervageorder.presentation.menulist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBar
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBarState
import com.example.bervageorder.presentation.menulist.state.ErrorScreen
import com.example.bervageorder.presentation.menulist.state.LoadingScreen
import com.example.bervageorder.presentation.menulist.state.MenuList

/**
 *  1. 선언형 프로그래밍 -> What
 *  - CreateObject("Apple", "Red")  <- 여기서 전달하는 param은 모드 상태 값이다. 고정값이 아니다.
 *  - Red Apple
 *  2. 명령형 프로그래밍 -> 시키는데로
 *   - val apple = Apple()
 *   - apple.color = Red
 *   - apple = Red Apple
 */

/**
 *  Compose를 다를 때 가장 중요한 포인트
 *  - 상태를 잘 다를줄 알아야 한다.
 *  - 모든 Composable에 param으로 data를 전달할 때에는 상태를 전달한다.
 *  - R.string.등의 고정값은 전달하지 않는다.
 *  - 리컴포지션이 얼마나 일어나는지는 신경쓰지 마라, 그냥 상태를 어떻게 관리하고, 전달하는지에 대해 고심할 것
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuListMainScreen(
    modifier: Modifier = Modifier,
    viewModel: MenuListViewModel = hiltViewModel(),
    navigateToOrderDetail: (String) -> Unit,
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BeverageOrderTopAppBar(
                state = BeverageOrderTopAppBarState.MenuListTitle,
                navigateUp = { navigateUp() }
            )
        },
    ) { paddingValues ->
        when(uiState) {
            is MenuListUiState.None -> {}
            is MenuListUiState.Loading -> { LoadingScreen() }
            is MenuListUiState.Error -> { ErrorScreen() }
            is MenuListUiState.Success -> {
                MenuList(
                    modifier = modifier.padding(paddingValues),
                    menuGrouped = (uiState as MenuListUiState.Success).menuMap,
                    navigateToOrderDetail = navigateToOrderDetail
                )
            }
        }
    }
}