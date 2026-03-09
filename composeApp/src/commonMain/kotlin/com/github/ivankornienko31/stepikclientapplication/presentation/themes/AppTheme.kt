package com.github.ivankornienko31.stepikclientapplication.presentation.themes

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
expect fun RandomAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
)