package com.github.ivankornienko31.stepikclientapplication.screens.login.data

import com.github.ivankornienko31.stepikclientapplication.screens.login.domain.LoginRepository
import com.github.ivankornienko31.stepikclientapplication.screens.login.domain.isValidEmail
import kotlinx.coroutines.delay

class LoginRepositoryImpl : LoginRepository {
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