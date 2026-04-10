package com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.entity

data class Token(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val scope: String,
    val refreshToken: String
)