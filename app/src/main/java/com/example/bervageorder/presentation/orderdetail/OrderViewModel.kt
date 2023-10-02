package com.example.bervageorder.presentation.orderdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.BeverageOrderDestinationArg
import com.example.bervageorder.domain.usecase.GetMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMenuUseCase: GetMenuUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<OrderDetail.OrderDetailUiState> = MutableStateFlow(
        OrderDetail.OrderDetailUiState())
    val uiState = _uiState.asStateFlow()

    val menuId: String = savedStateHandle.get<String>(BeverageOrderDestinationArg.MENU_ID_ARG).orEmpty()

    init {
        Timber.d("Current Menu :: ${menuId}")
        getCurrentMenu()
    }

    private fun getCurrentMenu() {
        viewModelScope.launch {
            getMenuUseCase.getMenuById(menuId = menuId)
                .onSuccess { menu ->
                    Timber.d("getCurrentMenu() result :: ${menu}")
                    _uiState.update { uiState ->
                        uiState.copy(
                            menu = menu
                        )
                    }
                }
                .onFailure {
                    Timber.w("getCurrentMenu() ERROR :: ${it.message}")
                }
        }
    }
}