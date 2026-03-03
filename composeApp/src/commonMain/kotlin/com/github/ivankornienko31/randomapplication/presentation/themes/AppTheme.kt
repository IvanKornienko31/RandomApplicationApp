package com.github.ivankornienko31.randomapplication.presentation.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * TODO: в дальнейшем добавить документацию для темы приложения
 * @author Иван Корниенко
 */

val DarkColorScheme = darkColorScheme(
    primary = Orange40,
    secondary = DimmedOrange40,
    tertiary = LightOrange40
)

val LightColorScheme = lightColorScheme(
    primary = Orange40,
    secondary = DimmedOrange80,
    tertiary = LightOrange80
)

@Composable
expect fun RandomAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
)