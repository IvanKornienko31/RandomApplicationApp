package com.github.ivankornienko31.stepikclientapplication.screens.login.domain

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}