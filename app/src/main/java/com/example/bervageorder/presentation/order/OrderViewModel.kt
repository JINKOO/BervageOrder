package com.example.bervageorder.presentation.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.domain.usecase.GetOrderMenuUseCase
import com.example.bervageorder.navigation.BeverageOrderDestinationArg.MENU_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.StringBuilder
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getOrderMenuUseCase: GetOrderMenuUseCase
) : ViewModel() {

    val menuId: String = savedStateHandle.get<String>(MENU_ID_ARG).orEmpty()

    private val _uiState: MutableStateFlow<Order.OrderUiState> =
        MutableStateFlow(Order.OrderUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getOrderMenu()
    }

    private fun getOrderMenu() {
        viewModelScope.launch {
            Timber.i("getOrderMenu() :: ${menuId}")
            getOrderMenuUseCase.getOrderMenu(menuId = menuId)
                .onSuccess { orderMenu ->
                    _uiState.update {
                        it.copy(
                            menu = orderMenu.menu,
                            optionListString = formatOptionListToString(orderMenu.optionList)
                        )
                    }
                }
                .onFailure {
                    Timber.w("getOrderMenu() ERROR :: ${it.message}")
                }
        }
    }

    private fun formatOptionListToString(optionList: List<String>): String {
        val stringBuilder = StringBuilder()
        optionList.forEachIndexed { index, option ->
            stringBuilder.append(option).also {
                if(index != optionList.lastIndex) {
                    it.append("/ ")
                }
            }
        }
        return stringBuilder.toString()
    }
}