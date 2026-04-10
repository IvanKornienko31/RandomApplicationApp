package com.github.ivankornienko31.stepikclientapplication.screens.main.data

import com.github.ivankornienko31.StepikClientApp
import com.github.ivankornienko31.stepikclientapplication.datastore.AuthPreferences
import com.github.ivankornienko31.stepikclientapplication.screens.auth.data.StepikAuthConfig
import com.github.ivankornienko31.stepikclientapplication.screens.auth.data.dto.TokenDto
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.formUrlEncode
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64

fun stepikHttpClient(authPreferences: AuthPreferences): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    // Используем Napier для вывода в консоль
                    Napier.i(message, tag = "KtorClient")
                }
            }

            level = LogLevel.ALL
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = authPreferences.accessToken.firstOrNull()
                    val refreshToken = authPreferences.refreshToken.firstOrNull()

                    if (accessToken != null && refreshToken != null) {
                        BearerTokens(accessToken, refreshToken)
                    } else null
                }

                refreshTokens {
                    val refresh = authPreferences.refreshToken.firstOrNull() ?: return@refreshTokens null

                    try {
                        val credentials = "${StepikClientApp.STEPIK_CLIENT_ID}:${StepikClientApp.STEPIK_CLIENT_SECRET}"
                        val basicAuth = Base64.encode(credentials.encodeToByteArray())

                        val response = client.post(StepikAuthConfig.TOKEN_URL) {
                            markAsRefreshTokenRequest()
                            header("Authorization", "Bearer $basicAuth")
                            contentType(ContentType.Application.FormUrlEncoded)
                            setBody(parameters {
                                append("grant_type", "refresh_token")
                                append("refresh_token", refresh)
                            }.formUrlEncode())
                        }.body<TokenDto>()

                        authPreferences.saveTokens(response.accessToken, response.refreshToken)
                        BearerTokens(response.accessToken, response.refreshToken)
                    } catch (e: Exception) {
                        authPreferences.clearTokens()
                        null
                    }
                }

                sendWithoutRequest { request ->
                    request.url.host == "stepik.org" && !request.url.encodedPath.contains("/oauth2/")
                }
            }
        }
    }
}
