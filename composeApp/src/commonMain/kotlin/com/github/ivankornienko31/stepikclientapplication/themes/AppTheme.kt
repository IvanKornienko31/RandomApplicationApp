package com.github.ivankornienko31.stepikclientapplication.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * TODO: в дальнейшем добавить документацию для темы приложения
 * @author Иван Корниенко
 */

val DarkColorScheme = darkColorScheme(
    primary = primaryColor,
    onPrimary = onPrimaryColor,
)

val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    onPrimary = onPrimaryColor,
)

@Composable
expect fun StepikAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
)