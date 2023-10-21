package com.example.bervageorder.presentation.order

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
import com.example.bervageorder.presentation.order.state.OrderUiState
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderMainScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToIntro: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            BeverageOrderTopAppBar(
                state = BeverageOrderTopAppBarState.OrderTitle,
                navigateUp = {
                    Timber.d("onClick NavigateUp()")
                    navigateUp()
                }
            )
        },
    ) { paddingValues ->
        when(uiState) {
            is OrderUiState.None -> {}
            is OrderUiState.Loading -> { LoadingScreen() }
            is OrderUiState.Success -> {
                OrderScreen(
                    modifier = modifier.padding(paddingValues),
                    menu = (uiState as OrderUiState.Success).menu,
                    optionList = (uiState as OrderUiState.Success).optionListString,
                    navigateToIntro = {
                        viewModel.clearAll()
                        navigateToIntro()
                    }
                )
            }
            is OrderUiState.Error -> { ErrorScreen(messageId = (uiState as OrderUiState.Error).errorMessage)}
        }
    }
}