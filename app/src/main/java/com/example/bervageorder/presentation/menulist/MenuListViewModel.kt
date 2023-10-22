package com.example.bervageorder.presentation.menulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.domain.usecase.GetMenuListUseCase
import com.example.bervageorder.presentation.menulist.state.MenuListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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
    private val getMenuListUseCase: GetMenuListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MenuListUiState>(MenuListUiState.None)
    val uiState: StateFlow<MenuListUiState> = _uiState.asStateFlow()

    init {
//        getMenuList()
        getMenuListFlow()
    }

    /**
     *  TODO 2회차 질문 :: 아래 로직 처럼, 비즈니스 로직이 ViewModel에 존재하면 안된다.
     *   ViewModel은 말 그대로 View에 대한 Model이다.
     *   따라서, View에 필요한 data들만 가공하는 역할을 하도록 수정한다.
     */
//    private fun getMenuList() {
//        _uiState.update { MenuListUiState.Loading }
//        viewModelScope.launch {
//            getMenuListUseCase.getMenuList()
//                .onSuccess { menuList ->
//                    Timber.d("initMenuList() :: ${menuList.size}")
//                    _uiState.update { MenuListUiState.Success(menuList.groupBy { it.type }) }
//                }
//                .onFailure {
//                    Timber.w("initMenuList() ERROR :: ${it.message}")
//                    _uiState.update { MenuListUiState.Error(errorMessage = R.string.title_error_message) }
//                }
//        }
//    }

    private fun getMenuListFlow() {
        // TODO 2회차 질문 :: launchIn을 사용할 때, 본 함수를 suspend로 만들어야하는지?
        getMenuListUseCase.getMenuListFlow()
            .onEach { menuList ->
                Timber.d("$menuList")
                _uiState.update { MenuListUiState.Success(menuMap = menuList.groupBy { it.type }) }
            }
            .onStart {
                Timber.d("onStart")
                _uiState.update { MenuListUiState.Loading }
            }
            .onCompletion {
                Timber.d("onCompletion")
            }
            .catch {
                Timber.d("ERROR ${it.message}")
            }
            .launchIn(viewModelScope)
    }
}
//        viewModelScope.launch {
//            getMenuListUseCase.getMenuListFlow()
//                .catch {
//                    Timber.e("${it.message}")
//                    _uiState.update { MenuListUiState.Error(errorMessage = R.string.title_error_message) }
//                }
//                .collectLatest { menuList ->
//                    _uiState.update { MenuListUiState.Success(menuMap = menuList.groupBy { it.type }) }
//                }
//        }
//    }
// }
