package com.github.ivankornienko31.stepikclientapplication.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ivankornienko31.stepikclientapplication.auth.domain.usecase.AuthLoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthScreenViewModel(
    private val authLoginUseCase: AuthLoginUseCase
) : ViewModel() {
    private val _screenState: MutableStateFlow<AuthScreenState> = MutableStateFlow(
        AuthScreenState.WebViewAuth
    )
    val screenState: StateFlow<AuthScreenState> = _screenState.asStateFlow()

    fun onCodeReceived(code: String) {
        _screenState.update {
            AuthScreenState.Loading
        }

        viewModelScope.launch {
            authLoginUseCase(code).onSuccess {
                _screenState.update { AuthScreenState.Success }
            }.onFailure {
                _screenState.update { AuthScreenState.Error("Some Error") }
            }
        }
    }
}