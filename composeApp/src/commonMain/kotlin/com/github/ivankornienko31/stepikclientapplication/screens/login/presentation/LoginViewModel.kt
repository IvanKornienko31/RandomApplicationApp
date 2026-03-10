package com.github.ivankornienko31.stepikclientapplication.screens.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ivankornienko31.stepikclientapplication.screens.login.data.LoginRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.screens.login.domain.LoginUseCase
import com.github.ivankornienko31.stepikclientapplication.screens.login.domain.isValidEmail
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val loginUseCase: LoginUseCase = LoginUseCase(LoginRepositoryImpl())
    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events: SharedFlow<LoginUiEvent> = _events.asSharedFlow()

    fun onEmailChanged(email: String) {
        _state.update {
            it.copy(
                email = email,
                isEmailError = false, // сбрасываем ошибку при наборе текста
                isLoginButtonActive = checkButtonActive(email, it.password)
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _state.update {
            it.copy(
                password = password,
                isPasswordError = false, // сбрасываем ошибку при наборе текста
                isLoginButtonActive = checkButtonActive(it.email, password)
            )
        }
    }

    fun onEmailFocusChanged(isFocused: Boolean) {
        val currentState = _state.value
        if (isFocused) {
            _state.update { it.copy(wasEmailFocused = true) }
        } else {
            if (currentState.wasEmailFocused) {
                val isError = !currentState.email.trim().isValidEmail()
                _state.update { it.copy(isEmailError = isError) }
            }
        }
    }

    fun onPasswordFocusChanged(isFocused: Boolean) {
        val currentState = _state.value
        if (isFocused) {
            _state.update { it.copy(wasPasswordFocused = true) }
        } else {
            if (currentState.wasPasswordFocused) {
                // Пароль должен быть длиннее 8 символов
                val isError = currentState.password.length <= 8
                _state.update { it.copy(isPasswordError = isError) }
            }
        }
    }

    private fun checkButtonActive(email: String, password: String): Boolean {
        return email.trim().isValidEmail() && password.length >= 8
    }

    fun onLoginClicked() {
        val currentState = _state.value
        if (!currentState.isLoginButtonActive) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // Вызываем UseCase
            val result = loginUseCase(currentState.email, currentState.password)

            _state.update { it.copy(isLoading = false) }

            result.onSuccess {
                _events.emit(LoginUiEvent.LoginSuccessEvent)
            }.onFailure { exception ->
                _events.emit(LoginUiEvent.ShowError(exception.message ?: "Error"))
            }
        }
    }
}