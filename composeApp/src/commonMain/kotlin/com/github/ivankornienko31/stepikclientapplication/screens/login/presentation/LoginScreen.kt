package com.github.ivankornienko31.stepikclientapplication.screens.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.LocalAutofillHighlightBrush
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.ivankornienko31.stepikclientapplication.screens.login.domain.isValidEmail
import com.github.ivankornienko31.stepikclientapplication.themes.CustomDimens
import com.github.ivankornienko31.stepikclientapplication.themes.CustomModifiers
import com.github.ivankornienko31.stepikclientapplication.themes.CustomShapes
import com.github.ivankornienko31.stepikclientapplication.themes.CustomTextStyles
import com.github.ivankornienko31.stepikclientapplication.themes.StepikAppTheme
import com.skydoves.compose.stability.runtime.TraceRecomposition
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import stepikclientapplication.composeapp.generated.resources.Res
import stepikclientapplication.composeapp.generated.resources.login_button_action
import stepikclientapplication.composeapp.generated.resources.input_field_incorrect_email_format
import stepikclientapplication.composeapp.generated.resources.input_field_incorrect_password_length
import stepikclientapplication.composeapp.generated.resources.input_field_is_empty
import stepikclientapplication.composeapp.generated.resources.login_screen_greeting
import stepikclientapplication.composeapp.generated.resources.login_screen_hint
import stepikclientapplication.composeapp.generated.resources.text_field_email_label
import stepikclientapplication.composeapp.generated.resources.text_field_email_placeholder
import stepikclientapplication.composeapp.generated.resources.text_field_password_label
import stepikclientapplication.composeapp.generated.resources.text_field_password_placeholder

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
    viewModel: LoginViewModel = viewModel { LoginViewModel() },
    onNavigateToMain: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> {
                    Napier.d(tag = "Login") { "Успешная авторизация, переходим на MainScreen!" }
                    onNavigateToMain()
                }

                is LoginUiEvent.ShowError -> {
                    Napier.d(tag = "Login") { "Ошибка: ${event.message}" }
                }
            }
        }
    }

    Scaffold(modifier = CustomModifiers.scaffoldModifier) { innerPadding ->
        BoxWithConstraints(
            modifier = CustomModifiers.Companion.constraintModifier(innerPadding),
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
                    HelperText()

                    InputContent(
                        uiState = uiState,
                        viewModel = viewModel
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
                    HelperText()

                    InputContent(
                        uiState = uiState,
                        viewModel = viewModel
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
 * [HelperText] является подписью над (или слева в landscape orientation) полями ввода
 *
 * При повороте телефона в горизонтальное положение данный `Composable` перемещается в левую сторону экрана
 *
 * Общие `Composables`:
 * - [Text]
 *
 * Весь текст в [HelperText] добавлен через `strings.xml` из `composeResources`
 *
 * @author Иван Корниенко*/

@Composable
fun HelperText() {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = CustomDimens.spaceBetweenElements,
                alignment = Alignment.CenterVertically
            ),
        ) {
            Text(
                text = stringResource(Res.string.login_screen_greeting),
                style = CustomTextStyles.headerStyle
            )

            Text(
                text = stringResource(Res.string.login_screen_hint),
                style = CustomTextStyles.mainTextStyle
            )
        }
    }
}

/**
 * [InputContent] является `Composable`, содержащим [OutlinedTextField] и [Button]
 *
 * Необходимость в этом `Composable` заключается в том, чтобы разграничить текст перед полями ввода от остального контента, если экран находится в горизонтальном положении
 *
 * При повороте телефона в горизонтальное положение `Composable` перемещается в правую сторону экрана
 *
 * Общие `Composables`:
 * - [EmailTextField] - поле ввода для email
 * - [PasswordTextField] - поле ввода для пароля
 * - [AcceptButton] - кнопка "отправки" данных
 *
 * Дополнительные `Composables`:
 * - [CompositionLocalProvider] - необходимо для прозрачной заливки при автоподстановке данных в поля ввода
 *
 * @param viewModel skskskskssks
 * @param uiState Stskskskskssks
 *
 * @author Иван Корниенко*/
@Stable
@Composable
fun InputContent(viewModel: LoginViewModel, uiState: LoginUiState) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = CustomDimens.spaceBetweenElements,
                alignment = Alignment.CenterVertically
            )
        ) {
            val autofillHighlightColor = SolidColor(Color.Transparent)

            CompositionLocalProvider(
                LocalAutofillHighlightBrush provides autofillHighlightColor
            ) {
                EmailTextField(
                    email = uiState.email,
                    isError = uiState.isEmailError,
                    onEmailChanged = viewModel::onEmailChanged,
                    onFocusChanged = viewModel::onEmailFocusChanged
                )

                PasswordTextField(
                    password = uiState.password,
                    isError = uiState.isPasswordError,
                    onPasswordChanged = viewModel::onPasswordChanged,
                    onFocusChanged = viewModel::onPasswordFocusChanged
                )
            }

            AcceptButton(
                isActive = uiState.isLoginButtonActive,
                onClick = viewModel::onLoginClicked
            )
        }
    }
}

/**
 * [EmailTextField] является `Composable`, содержащим [OutlinedTextField] для ввода Email
 *
 * Общие `Composables`:
 * - [OutlinedTextField] - поле ввода с обводкой
 *
 * Для обработки ввода и хранения текста используются параметры `value` и `onValueChange`
 * Для обработки ошибок при вводе текста используются параметры `isError`, `onValueChange`.
 * Для обработки состояния фокуса на поле в [Modifier] прокидывается [onFocusChanged]
 *
 * `supportingText` появляется только в том случае, если при вводе допущены ошибки
 * Так как логика обработки ошибок внедрена в метод [onFocusChanged], ошибка появляется только тогда, когда уходит фокус с поля ввода
 *
 * В функции [EmailTextField] добавлено логирование, срабатывающее при получении правильной почты
 *
 * Весь текст в `label` и `placeholder` добавлен через `strings.xml` из `composeResources`
 *
 * @param email авыпыфвпыв
 * @param isError пыпыцурккаы
 * @param onEmailChanged рпваыфшпргщфыяпрамгыщфрпингфмы
 * @param onFocusChanged рпваыфшпргщфыяпрамгыщфрпингфмы
 *
 * @author Иван Корниенко*/

@Composable
fun EmailTextField(
    email: String,
    isError: Boolean,
    onEmailChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChanged,
        isError = isError,
        supportingText = if (isError) {
            {
                Text(
                    text = stringResource(
                        if (email.trim().isEmpty()) Res.string.input_field_is_empty
                        else Res.string.input_field_incorrect_email_format
                    )
                )
            }
        } else null,
        label = { Text(text = stringResource(Res.string.text_field_email_label)) },
        placeholder = { Text(text = stringResource(Res.string.text_field_email_placeholder)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        shape = CustomShapes.unifiedShape,
        modifier = CustomModifiers.inputModifier.semantics {
            contentType = ContentType.EmailAddress
        }.onFocusChanged { focusState ->
            onFocusChanged(focusState.isFocused)
        },
    )

    LaunchedEffect(isError, email) {
        if (!isError && email.trim().isValidEmail()) {
            Napier.d { "Congratulations! You've got a correct email: $email" }
        }
    }
}

/**
 * [PasswordTextField] является `Composable`, содержащим [OutlinedTextField] для ввода пароля
 *
 * Общие `Composables`:
 * - [OutlinedTextField] - поле ввода с обводкой
 *
 * Для обработки ввода и хранения текста используются параметры `value` и `onValueChange`
 *
 * `supportingText` появляется только в том случае, если при вводе допущены ошибки
 * Так как логика обработки ошибок внедрена в метод [onFocusChanged], ошибка появляется только тогда, когда уходит фокус с поля ввода
 *
 * В функции [PasswordTextField] добавлено логирование, срабатывающее при вводе символа и отображающий пароль в логах
 *
 * **ВНИМАНИЕ: указанный выше функционал является крайне плохим с точки зрения безопасности и будет удален в следующем обновлении**
 *
 * Весь текст в `label` и `placeholder` добавлен через `strings.xml` из `composeResources`
 *
 * @param password длякопшгц9урпгуцп
 * @param isError копшгц9урпгуцп
 * @param onPasswordChanged копшгц9урпгуцп
 * @param onFocusChanged копшгц9урпгуцп
 *
 * @author Иван Корниенко*/

@Composable
fun PasswordTextField(
    password: String,
    isError: Boolean,
    onPasswordChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChanged,
        isError = isError,
        supportingText = if (isError) {
            {
                Text(text = stringResource(Res.string.input_field_incorrect_password_length))
            }
        } else null,
        label = { Text(text = stringResource(Res.string.text_field_password_label)) },
        placeholder = { Text(text = stringResource(Res.string.text_field_password_placeholder)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = CustomShapes.unifiedShape,
        modifier = CustomModifiers.inputModifier
            .semantics {
                contentType = ContentType.Password
            }
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        visualTransformation = PasswordVisualTransformation()
    )

    LaunchedEffect(password) {
        Napier.d(tag = "New Value Detected") { "Current value is $password" }
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
fun AcceptButton(isActive: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = isActive,
        modifier = CustomModifiers.buttonModifier,
        shape = CustomShapes.unifiedShape,
        content = {
            Text(
                text = stringResource(Res.string.login_button_action),
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
            onNavigateToMain = { }
        )
    }
}
