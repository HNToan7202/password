package com.example.yourpass.presentation.base

data class UIState(
    val state: UIStateType = UIStateType.IDLE,
    val errorMessage: String? = null
)