package com.example.bervageorder.presentation.menulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.domain.usecase.GetMenuListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MenuListViewModel @Inject constructor(
    private val getMenuListUseCase: GetMenuListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuList.MenuListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initMenuList()
    }

    private fun initMenuList() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getMenuListUseCase.getMenuList()
                .onSuccess { menuList ->
                    Timber.d("initMenuList() :: ${menuList.size}")
                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            menuList = menuList
                        )
                    }
                }
                .onFailure {
                    Timber.w("initMenuList() ERROR :: ${it.message}")
                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false
                        )
                    }
                }
        }
    }
}