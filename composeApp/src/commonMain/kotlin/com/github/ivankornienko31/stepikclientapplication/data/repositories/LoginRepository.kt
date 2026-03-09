package com.github.ivankornienko31.stepikclientapplication.data.repositories

import com.github.ivankornienko31.stepikclientapplication.domain.repositories.ILoginRepository
import com.github.ivankornienko31.stepikclientapplication.domain.validators.isValidEmail
import kotlinx.coroutines.delay

class LoginRepositoryImpl : ILoginRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(2000)

        val isEmailValid = email.isValidEmail()
        val isPasswordValid = password.length > 8

        return if (isEmailValid && isPasswordValid) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Неверный логин или пароль"))
        }
    }
}