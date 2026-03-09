package com.github.ivankornienko31.stepikclientapplication.presentation.screens.login

sealed class LoginUiEvent {
    data object LoginSuccessEvent : LoginUiEvent()
    data class ShowError(val message: String) : LoginUiEvent()
}