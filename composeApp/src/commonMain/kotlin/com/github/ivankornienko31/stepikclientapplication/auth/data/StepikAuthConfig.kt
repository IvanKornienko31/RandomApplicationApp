package com.github.ivankornienko31.stepikclientapplication.auth.data

import com.github.ivankornienko31.StepikClientApp

object StepikAuthConfig {
    const val REDIRECT_URI: String = "https://ivan-kornienko.com"
    private const val AUTHORIZE_URL: String = "https://stepik.org/oauth2/authorize/"
    const val TOKEN_URL: String = "https://stepik.org/oauth2/token/"

    fun buildAuthorizeUrl(): String =
        "${AUTHORIZE_URL}?response_type=code&client_id=${StepikClientApp.STEPIK_CLIENT_ID}&redirect_uri=${REDIRECT_URI}"
}