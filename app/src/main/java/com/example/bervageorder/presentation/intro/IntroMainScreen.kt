package com.example.bervageorder.presentation.intro

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
import com.example.bervageorder.presentation.intro.state.IntroScreen
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroMainScreen(
    modifier: Modifier = Modifier,
    viewModel: IntroViewModel = hiltViewModel(),
    navigateToMenuList: () -> Unit = {}
) {
    // TODO 2회차 질문 :: uiState Collect를 Scaffold내에서 해야하는지, 아니면 아래처럼 해야하는지?
    //  architecture-sample의 TODO APP을 보면 Scaffold내에서 collect.
    // 답변 : Scaffold 밖에서 Collect하는 것은 화면 큰 단위가 변경 될 때. Error Full Screen 등을 uiState에 따라 분기할 때
    // 하위 Composable에서도 collect가능함.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Timber.d("IntroMainScreen")

    Scaffold(
        topBar = {
            BeverageOrderTopAppBar(
                state = BeverageOrderTopAppBarState.IntroTitle,
                navigateUp = {}
            )
        }
    ) { paddingValues ->
        when(uiState) {
            is IntroUiState.None -> Unit
            is IntroUiState.Loading -> { LoadingScreen() }
            is IntroUiState.Success -> {
                IntroScreen(
                    modifier = modifier.padding(paddingValues = paddingValues),
                    introTitleId = (uiState as IntroUiState.Success).introTitleId,
                    navigateToMenuList = navigateToMenuList
                )
            }
            is IntroUiState.Error -> { ErrorScreen(errorState = (uiState as IntroUiState.Error)) }
        }
    }
}