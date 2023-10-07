package com.example.bervageorder.presentation.menudetail

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.navigation.BeverageOrderDestinationArg
import com.example.bervageorder.R
import com.example.bervageorder.domain.usecase.GetMenuUseCase
import com.example.bervageorder.domain.usecase.SetOptionListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _uiState: MutableStateFlow<MenuDetail.OrderDetailUiState> = MutableStateFlow(
        MenuDetail.OrderDetailUiState()
    )
    val uiState = _uiState.asStateFlow()

    val menuId: String =
        savedStateHandle.get<String>(BeverageOrderDestinationArg.MENU_ID_ARG).orEmpty()

    private val selectedOptionMap: MutableMap<Int, String> = mutableMapOf()

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

    fun addOption(optionId: Int, option: String) {
        Timber.d("selectedOption() :: ${optionId} / ${option}")
        selectedOptionMap[optionId] = option
    }

    fun clearOption() {
        selectedOptionMap.clear()
    }

    fun setSelectedOptions() {
        Timber.d("selectedOption() :: ${selectedOptionMap.size}")
        if (selectedOptionMap.isNullOrEmpty()) {
            _uiState.update { it.copy(isShowMessage = true) }
            return
        }
        viewModelScope.launch {
            val optionList = selectedOptionMap.toMap().toList().map { it.second }
            setOptionListUseCase.setOptionList(menuId, optionList)
                .onSuccess {
                    _uiState.update { it.copy(isNavigateToNext = true) }
                }
                .onFailure {
                    Timber.w("setSelectedOptions() ERROR :: ${it.message}")
                }
        }
    }

    fun showToastDone() {
        _uiState.update { it.copy(isShowMessage = false) }
    }
}

data class MenuOption(
    @StringRes val title: Int,
    @ColorRes val selectedTextColor: Color,
    @ColorRes val unSelectedTextColor: Color,
    @ColorRes val selectedBackGroundColor: Color,
    @ColorRes val unSelectedBackGroundColor: Color
)

val defaultOptionLists = listOf(
    MenuOption(
        title = R.string.button_hot,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    ),
    MenuOption(
        title = R.string.button_ice,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    )
)

val deCaffeineOptionList = listOf(
    MenuOption(
        title = R.string.button_caffeine,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    ),
    MenuOption(
        title = R.string.button_decaffeine,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    )
)

val iceQuantityOptionList = listOf(
    MenuOption(
        title = R.string.button_ice_small,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    ),
    MenuOption(
        title = R.string.button_ice_medium,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    ),
    MenuOption(
        title = R.string.button_ice_large,
        selectedTextColor = Color.White,
        unSelectedTextColor = Color.Black,
        selectedBackGroundColor = Color.DarkGray,
        unSelectedBackGroundColor = Color.LightGray,
    )
)