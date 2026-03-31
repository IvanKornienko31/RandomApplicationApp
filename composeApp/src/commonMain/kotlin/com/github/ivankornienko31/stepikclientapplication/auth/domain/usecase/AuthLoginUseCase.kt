package com.github.ivankornienko31.stepikclientapplication.auth.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.ivankornienko31.stepikclientapplication.auth.domain.repository.AuthRepository

class AuthLoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(code: String): Result<Unit> {
        return authRepository.exchangeCodeForToken(code = code).onSuccess {

        }.map { }
    }
}