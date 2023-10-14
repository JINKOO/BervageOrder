package com.example.bervageorder.presentation.menulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.R
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
     *  TODO 2회차 질문 :: 아래 로직 처럼, 비즈니스 로직이 ViewModel에 존재하면 안된다.
     *   ViewModel은 말 그대로 View에 대한 Model이다.
     *   따라서, View에 필요한 data들만 가공하는 역할을 하도록 수정한다.
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
                    _uiState.update { MenuListUiState.Error(errorMessage = R.string.title_error_message) }
                }
        }
    }
}