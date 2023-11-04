package com.example.bervageorder.presentation.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.R
import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.usecase.GetOrderMenuUseCase
import com.example.bervageorder.navigation.BeverageOrderDestinationArg.MENU_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getOrderMenuUseCase: GetOrderMenuUseCase,
    private val menuRepository: MenuRepository,
) : ViewModel() {

    private val menuId: String = savedStateHandle.get<String>(MENU_ID_ARG).orEmpty()

    private val _uiState: MutableStateFlow<OrderUiState> = MutableStateFlow(OrderUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getOrderMenu()
    }

    private fun getOrderMenu() {
        viewModelScope.launch {
            Timber.i("getOrderMenu() :: ${menuId}")
            getOrderMenuUseCase.getOrderMenu(menuId = menuId).collectLatest { menuOptions ->
                _uiState.update { OrderUiState.Success(menuOptions = menuOptions) }
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            menuRepository.clearAll()
        }
    }
}