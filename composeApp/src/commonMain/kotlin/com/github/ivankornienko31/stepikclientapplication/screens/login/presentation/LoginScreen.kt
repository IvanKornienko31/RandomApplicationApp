package com.github.ivankornienko31.stepikclientapplication.screens.login.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.github.ivankornienko31.stepikclientapplication.themes.CustomDimens
import com.github.ivankornienko31.stepikclientapplication.themes.CustomModifiers
import com.github.ivankornienko31.stepikclientapplication.themes.CustomShapes
import com.github.ivankornienko31.stepikclientapplication.themes.CustomTextStyles
import com.github.ivankornienko31.stepikclientapplication.themes.ImageAssets
import com.github.ivankornienko31.stepikclientapplication.themes.StepikAppTheme
import com.skydoves.compose.stability.runtime.TraceRecomposition
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import stepikclientapplication.composeapp.generated.resources.Res
import stepikclientapplication.composeapp.generated.resources.authorization_button

/**
 * [LoginScreen] является стартовым экраном приложения
 *
 * Общие `Composables`:
 * - [Text]
 * - [OutlinedTextField]
 * - [Button]
 *
 * Дополнительные `Composables`:
 * - [BoxWithConstraints] - необходимо для адаптации `Composable` при повороте экрана
 * - [CompositionLocalProvider] - необходимо для прозрачной заливки при автоподстановке данных в поля ввода
 *
 * В функции [LoginScreen] добавлено логирование, срабатывающее при получении данных из `Composable` [com.github.ivankornienko31.stepikclientapplication.screens.greeting.presentation.GreetingScreen]
 *
 * @param id Значение, которое передается при нажатии на кнопку в экране [com.github.ivankornienko31.stepikclientapplication.screens.greeting.presentation.GreetingScreen]
 * @author Иван Корниенко*/

@TraceRecomposition
@Composable
fun LoginScreen(
    id: String,
    onNavigateToAuth: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> {
                    Napier.d(tag = "Login") { "Успешная авторизация, переходим на MainScreen!" }
                    onNavigateToAuth()
                }

                is LoginUiEvent.ShowError -> {
                    Napier.d(tag = "Login") { "Ошибка: ${event.message}" }
                }
            }
        }
    }

    Scaffold(modifier = CustomModifiers.scaffoldModifier) { innerPadding ->
        BoxWithConstraints(
            modifier = CustomModifiers.constraintModifier(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            val isLandscape = maxWidth > maxHeight

            if (isLandscape) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = CustomDimens.spaceBetweenElements,
                        alignment = Alignment.CenterHorizontally
                    ),
                    modifier = CustomModifiers.flexModifier
                ) {
                    StepikLogo()

                    AuthorizationButton(
                        onClick = {
                            onNavigateToAuth()
                        }
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = CustomDimens.spaceBetweenElements,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = CustomModifiers.flexModifier
                ) {
                    StepikLogo()

                    AuthorizationButton(
                        onClick = {
                            onNavigateToAuth()
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(id) {
        Napier.d(tag = "Data From MainScreen") { "Received Data: $id" }
    }
}

/**
 * [AcceptButton] является `Composable`, содержащим [Button] для "отправки" данных. На данный момент при нажатии ничего не происходит, в параметре `onclick` стоит заглушка
 *
 * Общие `Composables`:
 * - [Button] - поле ввода с обводкой
 *
 * В параметре `onClick` добавлено логирование, срабатывающее при нажатии на кнопку
 *
 * Весь текст в `content` добавлен через `strings.xml` из `composeResources`
 *
 * @param isActive jgrgpjraseuirg
 * @param onClick hello
 *
 * @author Иван Корниенко*/

@Composable
private fun StepikLogo() {
    val isDarkThemeEnabled = isSystemInDarkTheme()

    val model: String = when {
        isDarkThemeEnabled -> ImageAssets.STEPIK_LOGO_DARK
        else -> ImageAssets.STEPIK_LOGO_LIGHT
    }

    AsyncImage(
        model = model,
        onSuccess = { Napier.d(tag = "Image state") { "Image was loaded successfully" } },
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun AuthorizationButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = CustomModifiers.buttonModifier,
        shape = CustomShapes.unifiedShape,
        content = {
            Text(
                text = stringResource(Res.string.authorization_button),
                style = CustomTextStyles.buttonFontStyle
            )
        },
    )
}

/**
 * Превью для свёрстанного экрана [LoginScreen].
 *
 * По умолчанию стоит английская локаль, также показывается фон приложения.
 *
 * [LoginScreen] обернут в [StepikAppTheme], чтобы применить цветовую палитру.
 *
 * @author Иван Корниенко*/

@Preview(showBackground = true, locale = "en")
@Composable
fun LoginScreenPreview() {
    StepikAppTheme {
        LoginScreen(
            id = "",
            onNavigateToAuth = { }
        )
    }
}
