package com.github.ivankornienko31.stepikclientapplication.presentation.screens.login

data class LoginUiState(
    val email: String = "",       // username
    val password: String = "",
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val wasEmailFocused: Boolean = false,
    val wasPasswordFocused: Boolean = false,
    val isLoading: Boolean = false,
    val isLoginButtonActive: Boolean = false
)