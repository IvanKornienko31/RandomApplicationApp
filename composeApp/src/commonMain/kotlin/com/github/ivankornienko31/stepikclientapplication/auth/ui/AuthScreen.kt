package com.github.ivankornienko31.stepikclientapplication.auth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.ivankornienko31.stepikclientapplication.auth.StepikAuthWebView
import com.github.ivankornienko31.stepikclientapplication.auth.data.StepikAuthConfig
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    viewModel: AuthScreenViewModel = koinViewModel(),
    onNavigateToMain: () -> Unit
) {
    val uiState by viewModel.screenState.collectAsStateWithLifecycle()

//    Scaffold { }

    when (val current = uiState) {
        is AuthScreenState.WebViewAuth -> {
            StepikAuthWebView(
                authorizeUrl = StepikAuthConfig.buildAuthorizeUrl(),
                redirectUri = StepikAuthConfig.REDIRECT_URI,
                onCodeReceived = viewModel::onCodeReceived,
                modifier = Modifier.fillMaxSize()
            )
        }
        is AuthScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is AuthScreenState.Success -> { onNavigateToMain() }
        is AuthScreenState.Error -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Ошибка: ${current.errorMessage}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
