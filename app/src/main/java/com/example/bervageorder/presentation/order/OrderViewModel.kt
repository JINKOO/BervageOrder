package com.example.bervageorder.presentation.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.R
import com.example.bervageorder.data.repository.MenuRepository
import com.example.bervageorder.domain.model.OptionType
import com.example.bervageorder.domain.usecase.GetOrderMenuUseCase
import com.example.bervageorder.navigation.BeverageOrderDestinationArg.MENU_ID_ARG
import com.example.bervageorder.presentation.order.state.OrderUiState
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
    private val getOrderMenuUseCase: GetOrderMenuUseCase,
    private val menuRepository: MenuRepository,
) : ViewModel() {

    val menuId: String = savedStateHandle.get<String>(MENU_ID_ARG).orEmpty()

    private val _uiState: MutableStateFlow<OrderUiState> = MutableStateFlow(OrderUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getOrderMenu()
    }

    private fun getOrderMenu() {
        viewModelScope.launch {
            Timber.i("getOrderMenu() :: $menuId")
            getOrderMenuUseCase.getOrderMenu(menuId = menuId)
                .onSuccess { orderMenu ->
                    _uiState.update {
                        OrderUiState.Success(
                            menu = orderMenu.menu,
                            optionListString = formatOptionListToString(orderMenu.optionList),
                        )
                    }
                }
                .onFailure {
                    Timber.w("getOrderMenu() ERROR :: ${it.message}")
                    _uiState.update { OrderUiState.Error(errorMessage = R.string.title_error_message) }
                }
        }
    }

    private fun formatOptionListToString(optionList: List<OptionType>): String {
        val stringBuilder = StringBuilder()
        optionList.forEachIndexed { index, option ->
            stringBuilder.append(option.value).also {
                if (index != optionList.lastIndex) {
                    it.append("/ ")
                }
            }
        }
        return stringBuilder.toString()
    }

    fun clearAll() {
        viewModelScope.launch {
            runCatching { menuRepository.clearAll() }
                .fold(
                    onSuccess = {
                        Result.success(Unit)
                    },
                    onFailure = {
                        Result.failure(it)
                    },
                )
                .onSuccess {
                }
                .onFailure {
                }
        }
    }

    fun foo(unit: Unit) {
        Timber.i("foo() :: $unit")
    }
}
