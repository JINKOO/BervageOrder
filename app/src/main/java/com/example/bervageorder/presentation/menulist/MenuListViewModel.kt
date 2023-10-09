package com.example.bervageorder.presentation.menulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.domain.usecase.GetMenuListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuListViewModel @Inject constructor(
    private val getMenuListUseCase: GetMenuListUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MenuListUiState> = MutableStateFlow(MenuListUiState.None)
    val uiState: StateFlow<MenuListUiState> = _uiState.asStateFlow()

    init {
        getMenuList()
    }

    private fun getMenuList() {
        _uiState.update { MenuListUiState.Loading }
        viewModelScope.launch {
            val result = getMenuListUseCase.getMenuList()
            _uiState.update { MenuListUiState(result) }
        }
    }
}
