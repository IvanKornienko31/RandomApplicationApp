package com.github.ivankornienko31.stepikclientapplication.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
actual fun StepikAuthWebView(
    authorizeUrl: String,
    redirectUri: String,
    onCodeReceived: (String) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Auth via WebView is not yet supported on iOS")
    }
}