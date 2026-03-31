package com.github.ivankornienko31.stepikclientapplication.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun StepikAuthWebView(
    authorizeUrl: String,
    redirectUri: String,
    onCodeReceived: (String) -> Unit,
    modifier: Modifier = Modifier
)