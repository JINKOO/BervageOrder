package com.example.bervageorder.presentation.menulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bervageorder.R
import com.example.bervageorder.domain.usecase.GetMenuListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
//        getMenuListFlow()
    }

    private fun getMenuListFlow() {
        // TODO 2회차 질문 :: launchIn을 사용할 때, 본 함수를 suspend로 만들어야하는지?
        // 답변 : Flow는 suspend 필요 없다.
        getMenuListUseCase.getMenuListFlow()
            .onEach {menuList ->
                Timber.d("$menuList")
                _uiState.update { MenuListUiState.Success(MenuListSubUiState.Success(menuMap = menuList.groupBy { it.type })) }
            }
            .onCompletion {
                Timber.d("onCompletion")
            }
            .catch {
                Timber.d("ERROR ${it.message}")
                _uiState.update { MenuListUiState.Error(errorMessage = R.string.title_error_message) }
            }
            .launchIn(viewModelScope)
    }

    private fun getMenuList() {
        viewModelScope.launch {
            getMenuListUseCase.getMenuList()
                .onSuccess { menuList ->
                    _uiState.update { MenuListUiState.Success(MenuListSubUiState.Success(menuMap = menuList.groupBy { it.type })) }
                }
                .onFailure {
                    Timber.d("ERROR ${it.message}")
                    _uiState.update { MenuListUiState.Error(errorMessage = R.string.title_error_message) }
                }
        }
    }
}