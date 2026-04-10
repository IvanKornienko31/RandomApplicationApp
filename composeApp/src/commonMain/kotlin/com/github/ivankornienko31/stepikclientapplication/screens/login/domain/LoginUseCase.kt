package com.github.ivankornienko31.stepikclientapplication.screens.login.domain

class LoginUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.login(email, password)
    }
}