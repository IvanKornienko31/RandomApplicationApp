package com.github.ivankornienko31.stepikclientapplication.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Данный файл представляет адаптацию темы приложения на iOS
 *
 * Так как у iOS нет фичи dynamicColor, переключение между темами происходит без его участия
 * @param darkTheme Флаг, определяющий текущую тему приложения
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
    val colorScheme =
        if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}