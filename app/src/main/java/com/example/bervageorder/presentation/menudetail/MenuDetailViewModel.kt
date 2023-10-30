package com.example.bervageorder.presentation.menudetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.R
import com.example.bervageorder.domain.model.Caffeine
import com.example.bervageorder.domain.model.IceQuantity
import com.example.bervageorder.domain.model.OrderMenuOption
import com.example.bervageorder.domain.model.OptionTypeSealed
import com.example.bervageorder.domain.model.Temperature
import com.example.bervageorder.domain.usecase.GetMenuUseCase
import com.example.bervageorder.domain.usecase.SetOptionListUseCase
import com.example.bervageorder.navigation.BeverageOrderDestinationArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MenuDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMenuUseCase: GetMenuUseCase,
    private val setOptionListUseCase: SetOptionListUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MenuDetailUiState> = MutableStateFlow(MenuDetailUiState.None)
    val uiState: StateFlow<MenuDetailUiState> = _uiState.asStateFlow()

    val menuId: String =
        savedStateHandle.get<String>(BeverageOrderDestinationArg.MENU_ID_ARG).orEmpty()

    private val _Order_menuOption: MutableStateFlow<OrderMenuOption> = MutableStateFlow(OrderMenuOption())
    val menuOption = _Order_menuOption.asStateFlow()

    init {
        getCurrentMenu()
    }

    private fun getCurrentMenu() {
        _uiState.update { MenuDetailUiState.Loading }
        viewModelScope.launch {
            getMenuUseCase.getMenuById(menuId = menuId)
                .onSuccess { menu ->
                    Timber.d("getCurrentMenu() result :: ${menu}")
                    _uiState.update {
                        MenuDetailUiState.Success(
                            menu = menu
                        )
                    }
                }
                .onFailure {
                    Timber.w("getCurrentMenu() ERROR :: ${it.message}")
                    _uiState.update { MenuDetailUiState.Error(messageId = R.string.title_error_message) }
                }
        }
    }

    fun addOption(option: OptionTypeSealed) {
       when(option) {
            is OptionTypeSealed.Ice -> _Order_menuOption.update { it.copy(temperature = Temperature.ICE)}
            is OptionTypeSealed.Hot -> _Order_menuOption.update { it.copy(temperature = Temperature.HOT)}
            is OptionTypeSealed.Caffeine -> _Order_menuOption.update { it.copy(caffeine = Caffeine.CAFFEINE)}
            is OptionTypeSealed.DeCaffeine -> _Order_menuOption.update { it.copy(caffeine = Caffeine.DE_CAFFEINE)}
            is OptionTypeSealed.IceMore -> _Order_menuOption.update{it.copy(iceQuantity = IceQuantity.MORE) }
            is OptionTypeSealed.IceNormal -> _Order_menuOption.update { it.copy(iceQuantity = IceQuantity.NORMAL) }
            is OptionTypeSealed.IceLess -> _Order_menuOption.update { it.copy(iceQuantity = IceQuantity.LESS) }
        }
    }

    fun clearOption() {
    }

    fun setSelectedOptions() {
        Timber.d("selectedOption() :: ${menuOption.value}")
    }
}