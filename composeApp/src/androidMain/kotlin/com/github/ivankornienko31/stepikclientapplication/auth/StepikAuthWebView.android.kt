package com.github.ivankornienko31.stepikclientapplication.auth

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun StepikAuthWebView(
    authorizeUrl: String,
    redirectUri: String,
    onCodeReceived: (String) -> Unit,
    modifier: Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                webViewClient = StepikAuthWebViewClient(redirectUri, onCodeReceived)
                loadUrl(authorizeUrl)
            }
        },
        modifier = modifier
    )
}

private class StepikAuthWebViewClient(
    private val redirectUri: String,
    private val onCodeReceived: (String) -> Unit
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest
    ): Boolean {
        val url = request.url.toString()
        if (url.startsWith(redirectUri)) {
                val code = request.url.getQueryParameter("code")
            if (code != null) {
                onCodeReceived(code)
            }
            return true
        }
        return false
    }
}