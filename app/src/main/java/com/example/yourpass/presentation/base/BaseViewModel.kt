package com.example.yourpass.presentation.base

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    // State của UI
    private val _uiState = mutableStateOf(UIState())
    val uiState: State<UIState> = _uiState

    fun setLoading() {
        _uiState.value = _uiState.value.copy(state = UIStateType.LOADING)
    }

    fun setError(message: String? = null) {
        _uiState.value = _uiState.value.copy(state = UIStateType.ERROR, errorMessage = message)
    }

    fun setSuccess() {
        _uiState.value = _uiState.value.copy(state = UIStateType.SUCCESS)
    }

    fun setIdle() {
        _uiState.value = _uiState.value.copy(state = UIStateType.IDLE)
    }
}