package com.github.ivankornienko31.stepikclientapplication.domain.repositories

interface ILoginRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}