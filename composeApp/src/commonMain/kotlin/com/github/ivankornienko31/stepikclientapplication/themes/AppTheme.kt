package com.github.ivankornienko31.stepikclientapplication.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
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
    surface = randomColor
)

val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    onPrimary = onPrimaryColor,
    surface = randomColor
)

@Composable
fun StepikAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}