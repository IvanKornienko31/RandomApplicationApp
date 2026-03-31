package com.github.ivankornienko31.stepikclientapplication.auth.ui

import org.jetbrains.compose.resources.StringResource

sealed interface AuthScreenState {
    data object WebViewAuth : AuthScreenState
    data object Loading : AuthScreenState
    data object Success : AuthScreenState
    data class Error(val errorMessage: String) : AuthScreenState
}
