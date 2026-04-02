package com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.usecase

import com.github.ivankornienko31.stepikclientapplication.datastore.AuthPreferences
import com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.repository.AuthRepository

class AuthLoginUseCase(
    private val authRepository: AuthRepository,
    private val authPreferences: AuthPreferences
) {
    suspend operator fun invoke(code: String): Result<Unit> {
        return authRepository.exchangeCodeForToken(code = code)
            .onSuccess { token ->
                authPreferences.saveTokens(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken
                )
            }
            .map { }
    }
}