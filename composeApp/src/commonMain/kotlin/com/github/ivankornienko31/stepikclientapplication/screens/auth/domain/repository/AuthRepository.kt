package com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.repository

import com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.entity.Token

interface AuthRepository {
    suspend fun exchangeCodeForToken(code: String): Result<Token>
}