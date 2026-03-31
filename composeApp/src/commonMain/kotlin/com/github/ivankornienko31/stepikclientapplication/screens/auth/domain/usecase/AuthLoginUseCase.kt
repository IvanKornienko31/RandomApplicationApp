package com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.usecase

import com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.repository.AuthRepository

class AuthLoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(code: String): Result<Unit> {
        return authRepository.exchangeCodeForToken(code = code).map { }
    }
}