package com.example.bervageorder.presentation.menulist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bervageorder.data.entity.MenuType
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.usecase.GetMenuListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MenuListViewModel @Inject constructor(
    private val getMenuListUseCase: GetMenuListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuList.MenuListUiState())
    val uiState = _uiState.asStateFlow()

    private val coffeeList = mutableListOf<Menu>()
    private val adeList = mutableListOf<Menu>()
    private val teaList = mutableListOf<Menu>()
    private val dessertList = mutableListOf<Menu>()

    init {
        getMenuList()
    }

    private fun getMenuList() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getMenuListUseCase.getMenuList()
                .onSuccess { menuList ->
                    Timber.d("initMenuList() :: ${menuList.size}")
                    val groupedMenuMap = menuList.groupBy { it.menuType }
                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            menuMap = groupedMenuMap
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