package com.example.bervageorder.presentation.menudetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.R
import com.example.bervageorder.domain.model.OptionType
import com.example.bervageorder.domain.usecase.GetMenuUseCase
import com.example.bervageorder.domain.usecase.SetOptionListUseCase
import com.example.bervageorder.navigation.BeverageOrderDestinationArg
import com.example.bervageorder.presentation.menudetail.state.MenuDetailUiState
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

    //
    private val selectedOptionMap: MutableMap<Int, OptionType> = mutableMapOf()

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

    fun addOption(optionId: Int, option: OptionType) {
        Timber.d("selectedOption() :: ${optionId} / ${option}")
        selectedOptionMap[optionId] = option
    }

    fun clearOption() {
        selectedOptionMap.clear()
    }

    fun setSelectedOptions() {
        Timber.d("selectedOption() :: ${selectedOptionMap.size}")
        viewModelScope.launch {
            val optionList = selectedOptionMap.toMap().toList().map { it.second }
            setOptionListUseCase.setOptionList(menuId, optionList)
                .onSuccess {
                    _uiState.update { MenuDetailUiState.AllOptionSelected }
                }
                .onFailure {
                    Timber.w("setSelectedOptions() ERROR :: ${it.message}")
                }
        }
    }
}