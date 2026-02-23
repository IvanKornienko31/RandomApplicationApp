package com.github.ivankornienko31.randomapplication

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.ivankornienko31.randomapplication.ui.screens.GreetingScreen
import com.github.ivankornienko31.randomapplication.ui.screens.LoginScreen

@Composable
@Preview
fun Router() {
    MaterialTheme {
        GreetingScreen({})
//        LoginScreen()
    }
}