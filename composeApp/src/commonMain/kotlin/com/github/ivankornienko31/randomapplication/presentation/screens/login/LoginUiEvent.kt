package com.github.ivankornienko31.randomapplication.presentation.screens.login

sealed class LoginUiEvent {
    data object LoginSuccessEvent : LoginUiEvent()
    data class ShowError(val message: String) : LoginUiEvent()
}