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
import com.example.bervageorder.presentation.common.error.ErrorScreen
import com.example.bervageorder.presentation.common.loading.LoadingScreen
import com.example.bervageorder.presentation.common.topbar.BeverageOrderTopAppBar
import com.example.bervageorder.presentation.common.topbar.BeverageOrderTopAppBarState
import com.example.bervageorder.presentation.menulist.state.MenuListUiState

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
        // TODO 2회차 질문 :: 최상위 Composable에서 sealed class로 정의된 각 스트린 타입별로 분기 하는데,
        //  전체 스크린이 아닌, 특정 부분에서만 로딩 표시 혹은 에러 스크린 표시하고 싶다면, 아래 방식 대신에 어떻게 처리해야 하는지??
        //  예를 들어, 현재 Screen에 topAppBar존재, 그 밑에 tabRow가 있을때,
        //  로딩은 tabRow 밑에서 보여주고, 에러 스크린은 topBar를 노출한 채로 전체 Screen인 경우,
        //  코드랩 TODO App 처럼 ViewModel에서 combine으로 처리..??
        when(uiState) {
            is MenuListUiState.None -> {}
            is MenuListUiState.Loading -> {
                LoadingScreen(modifier = modifier.padding(paddingValues = paddingValues))
            }
            is MenuListUiState.Error -> {
                ErrorScreen(
                    modifier = modifier.padding(paddingValues = paddingValues),
                    // TODO 2회차 질문 :: 여기서 형변환이 필요한 이유?
                    messageId = (uiState as MenuListUiState.Error).errorMessage
                )
            }
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