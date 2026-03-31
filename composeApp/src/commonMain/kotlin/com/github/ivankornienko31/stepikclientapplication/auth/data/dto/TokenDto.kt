package com.github.ivankornienko31.stepikclientapplication.auth.data.dto

import com.github.ivankornienko31.stepikclientapplication.auth.domain.entity.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class TokenDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("scope") val scope: String,
    @SerialName("refresh_token") val refreshToken: String
)

fun TokenDto.toDomain(): Token {
    return Token(
        accessToken = accessToken,
        tokenType = tokenType,
        expiresIn = expiresIn,
        scope = scope,
        refreshToken = refreshToken
    )
}