package com.github.ivankornienko31.stepikclientapplication.auth.domain.repository

import com.github.ivankornienko31.stepikclientapplication.auth.domain.entity.Token

interface AuthRepository {
    suspend fun exchangeCodeForToken(code: String): Result<Token>
}