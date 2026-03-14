package com.github.ivankornienko31.stepikclientapplication.screens.login.presentation

sealed class LoginUiEvent {
    data object LoginSuccessEvent : LoginUiEvent()
    data class ShowError(val message: String) : LoginUiEvent()
}