package com.github.ivankornienko31.stepikclientapplication.screens.auth.presentation

sealed interface AuthScreenState {
    data object WebViewAuth : AuthScreenState
    data object Loading : AuthScreenState
    data object Success : AuthScreenState
    data class Error(val errorMessage: String) : AuthScreenState
}
