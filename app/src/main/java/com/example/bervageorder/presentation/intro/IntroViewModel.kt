package com.example.bervageorder.presentation.intro

import androidx.lifecycle.ViewModel
import com.example.bervageorder.R
import com.example.bervageorder.presentation.intro.state.IntroUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState: MutableStateFlow<IntroUiState> = MutableStateFlow(IntroUiState.None)
    val uiState = _uiState.asStateFlow()

    init {
        getContent()
    }

    private fun getContent() {
        _uiState.update { IntroUiState.Success(introTitleId = R.string.title_intro_start_order) }
    }
}