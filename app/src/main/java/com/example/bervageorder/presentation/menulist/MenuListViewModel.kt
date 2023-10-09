package com.example.bervageorder.presentation.menulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.usecase.GetMenuListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 *  by 키워드 위임
 *
 *  view의 Model 즉 data를 들고 있는 주체,
 *  viewModel에서는 비즈니스 로직이 포함되면 안된다.
 */
@HiltViewModel
class MenuListViewModel @Inject constructor(
    private val getMenuListUseCase: GetMenuListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MenuListUiState>(MenuListUiState.None)
    val uiState: StateFlow<MenuListUiState> = _uiState.asStateFlow()

    init {
        getMenuList()
    }

    /**
     *  sealed class로 변경 할긋
     *  State를 만드는 방법은 State를 생성하는쪽에서 생성해야 한다. UiState
     */
    private fun getMenuList() {
        _uiState.update { MenuListUiState.Loading }
        viewModelScope.launch {
            delay(2000L)
            getMenuListUseCase.getMenuList()
                .onSuccess { menuList ->
                    Timber.d("initMenuList() :: ${menuList.size}")
                    _uiState.update { MenuListUiState.Success(menuList.groupBy { it.menuType }) }
                }
                .onFailure {
                    Timber.w("initMenuList() ERROR :: ${it.message}")
                    _uiState.update { MenuListUiState.Error }
                }
        }
    }

    // TODO 아래 방식으로 변경
//    private fun getMenuList() {
//        _uiState.update { MenuListUiState.Loading }
//        viewModelScope.launch {
//            val result = getMenuListUseCase.getMenuList()
//            _uiState.update { MenuListUiState(result) }
//        }
//    }
}