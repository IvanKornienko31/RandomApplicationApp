package com.github.ivankornienko31.randomapplication.ui.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    secondary = DimmedBlue80,
    tertiary = LightBlue80
)

val LightColorScheme = lightColorScheme(
    primary = Blue40,
    secondary = DimmedBlue40,
    tertiary = LightBlue40
)

@Composable
expect fun RandomAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
)