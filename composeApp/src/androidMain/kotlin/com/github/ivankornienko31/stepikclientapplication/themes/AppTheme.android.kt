package com.github.ivankornienko31.stepikclientapplication.themes

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Данный файл представляет адаптацию темы приложения на Android
 *
 * @param darkTheme Флаг, определяющий текущую тему приложения
 * @param dynamicColor Флаг, определяющий наличие функции Material You
 * @param content Composables, передаваемые как потомки
 *
 * @author Иван Корниенко
 */

@Composable
actual fun StepikAppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable (() -> Unit)
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme
    ) {
        content()
    }
}