package com.example.bervageorder.presentation.intro

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
import com.example.bervageorder.presentation.intro.state.IntroScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroMainScreen(
    modifier: Modifier = Modifier,
    viewModel: IntroViewModel = hiltViewModel(),
    navigateToMenuList: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            BeverageOrderTopAppBar(
                state = BeverageOrderTopAppBarState.IntroTitle,
                navigateUp = {}
            )
        }
    ) { paddingValues ->
        when(uiState) {
            is IntroUiState.None -> {}
            is IntroUiState.Loading -> {}
            is IntroUiState.Success -> {
                IntroScreen(
                    modifier = modifier.padding(paddingValues = paddingValues),
                    introTitleId = (uiState as IntroUiState.Success).introTitleId,
                    navigateToMenuList = navigateToMenuList
                )
            }
            is IntroUiState.Error -> {}
        }
    }
}