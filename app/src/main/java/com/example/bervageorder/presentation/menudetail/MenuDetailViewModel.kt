package com.example.bervageorder.presentation.menudetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.R
import com.example.bervageorder.domain.model.Caffeine
import com.example.bervageorder.domain.model.IceQuantity
import com.example.bervageorder.domain.model.OptionTypeSealed
import com.example.bervageorder.domain.model.OrderMenuOption
import com.example.bervageorder.domain.model.Temperature
import com.example.bervageorder.domain.usecase.GetMenuUseCase
import com.example.bervageorder.domain.usecase.PostOptionListUseCase
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
    private val postOptionListUseCase: PostOptionListUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MenuDetailUiState> =
        MutableStateFlow(MenuDetailUiState.None)
    val uiState: StateFlow<MenuDetailUiState> = _uiState.asStateFlow()

    val menuId: String =
        savedStateHandle.get<String>(BeverageOrderDestinationArg.MENU_ID_ARG).orEmpty()

    // 선택한 메뉴에 대한 option값들을 저장하는 stateFlow
    // StateFlow를 사용한 이유는, ICE를 선택한 경우, 얼음양을 조절하는 Row를 동적으로 보여주기 위해.
    private val _orderMenuOption: MutableStateFlow<OrderMenuOption> =
        MutableStateFlow(OrderMenuOption())
    val orderMenuOption = _orderMenuOption.asStateFlow()

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
                        MenuDetailUiState.Success(menu = menu)
                    }

                    // TODO 3회차 질문 :: OrderMenuOption Model에 옵션값 제외한, 기본정보 업데이트 로직을 여기서 처리해도 되는지..??
                    //  아니면 Repository단에서 처리하는 것이 맞는지
                    // 객체를 생성위치는 최종적으로 만들어지는 곳
                    menu?.let {
                        _orderMenuOption.update {
                            it.copy(
                                id = menu.id,
                                price = menu.price,
                                name = menu.name
                            )
                        }
                    }
                }
                .onFailure {
                    Timber.w("getCurrentMenu() ERROR :: ${it.message}")
                    _uiState.update { MenuDetailUiState.Error(messageId = R.string.title_error_message) }
                }
        }
    }

    /**
     *  각 메뉴에서 선택한 Option에 따라 orderMenuOption 상태를 update한다.
     *  data class의 copy를 사용.
     *  copy()를 사용하는 가장 큰 이유는 data class를 immutable하게 만들기 위해.
     *  즉, 복사본을 만들어서, 값을 update한다.
     */
    fun addOption(option: OptionTypeSealed) {
        when (option) {
            is OptionTypeSealed.Ice -> _orderMenuOption.update { it.copy(temperature = Temperature.ICE) }
            is OptionTypeSealed.Hot -> _orderMenuOption.update { it.copy(temperature = Temperature.HOT) }
            is OptionTypeSealed.Caffeine -> _orderMenuOption.update { it.copy(caffeine = Caffeine.CAFFEINE) }
            is OptionTypeSealed.DeCaffeine -> _orderMenuOption.update { it.copy(caffeine = Caffeine.DE_CAFFEINE) }
            is OptionTypeSealed.IceMore -> _orderMenuOption.update { it.copy(iceQuantity = IceQuantity.MORE) }
            is OptionTypeSealed.IceNormal -> _orderMenuOption.update { it.copy(iceQuantity = IceQuantity.NORMAL) }
            is OptionTypeSealed.IceLess -> _orderMenuOption.update { it.copy(iceQuantity = IceQuantity.LESS) }
        }
    }

    /**
     *  여기서 Option 객체를 만든다.
     *  사용자가 선택한 것을 만들때에는 최종적으로 만들어지는 시점에. 버튼 클릭했을 때, 객체를 만든다.
     */
    fun postMenuOptions() {
        Timber.d("selectedOption() :: ${orderMenuOption.value}")
        viewModelScope.launch {
            postOptionListUseCase.postOptionList(orderMenuOption.value)
                .onSuccess {
                    _uiState.update { MenuDetailUiState.AllOptionSelected }
                }
                .onFailure {
                    Timber.e("postMenuOptions() ERROR :: ${it.message}")
                    _uiState.update { MenuDetailUiState.Error(messageId = R.string.title_error_message) }
                }
        }
    }

    /**
     *  뒤로가기 선택 시, 알림 popUp을 노출하고, 선택한 Option값을 모두 null로 만든다.
     */
    fun clearOption() {
        Timber.d("clearOption()")
    }
}