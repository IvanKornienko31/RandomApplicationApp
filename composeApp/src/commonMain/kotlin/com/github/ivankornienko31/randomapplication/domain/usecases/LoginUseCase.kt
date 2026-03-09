package com.github.ivankornienko31.randomapplication.domain.usecases

import com.github.ivankornienko31.randomapplication.domain.repositories.ILoginRepository

class LoginUseCase(private val repository: ILoginRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.login(email, password)
    }
}