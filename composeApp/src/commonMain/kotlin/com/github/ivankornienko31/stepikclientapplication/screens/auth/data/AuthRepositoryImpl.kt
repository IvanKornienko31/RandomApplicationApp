package com.github.ivankornienko31.stepikclientapplication.screens.auth.data

import com.github.ivankornienko31.StepikClientApp
import com.github.ivankornienko31.stepikclientapplication.screens.auth.data.dto.TokenDto
import com.github.ivankornienko31.stepikclientapplication.screens.auth.data.dto.toDomain
import com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.entity.Token
import com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.repository.AuthRepository
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.runCatchingCancellable
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.formUrlEncode
import io.ktor.http.parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.io.encoding.Base64

class AuthRepositoryImpl(private val httpClient: HttpClient) :
    AuthRepository {
    override suspend fun exchangeCodeForToken(code: String): Result<Token> {
        return withContext(Dispatchers.IO) {
            runCatchingCancellable {
                val credentials =
                    "${StepikClientApp.STEPIK_CLIENT_ID}:${StepikClientApp.STEPIK_CLIENT_SECRET}"
                val basicAuth = Base64.encode(credentials.encodeToByteArray())
                val response = httpClient.post(StepikAuthConfig.TOKEN_URL) {
                    header("Authorization", "Basic $basicAuth")
                    contentType(ContentType.Application.FormUrlEncoded)
                    setBody(parameters {
                        append("grant_type", "authorization_code")
                        append("code", code)
                        append("redirect_uri", StepikAuthConfig.REDIRECT_URI)
                    }.formUrlEncode())
                }
                response.body<TokenDto>().toDomain()
            }
        }
    }
}
