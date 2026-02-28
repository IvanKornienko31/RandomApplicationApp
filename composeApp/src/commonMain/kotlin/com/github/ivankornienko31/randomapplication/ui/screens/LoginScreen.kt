package com.github.ivankornienko31.randomapplication.ui.screens

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.github.ivankornienko31.randomapplication.extensions.isValidEmail
import com.github.ivankornienko31.randomapplication.ui.themes.CustomDimens
import com.github.ivankornienko31.randomapplication.ui.themes.CustomModifiers
import com.github.ivankornienko31.randomapplication.ui.themes.CustomShapes
import com.github.ivankornienko31.randomapplication.ui.themes.CustomTextStyles
import com.github.ivankornienko31.randomapplication.ui.themes.RandomAppTheme
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import randomapplication.composeapp.generated.resources.Res
import randomapplication.composeapp.generated.resources.button_action
import randomapplication.composeapp.generated.resources.input_field_incorrect_email_format
import randomapplication.composeapp.generated.resources.input_field_is_empty
import randomapplication.composeapp.generated.resources.login_screen_greeting
import randomapplication.composeapp.generated.resources.login_screen_hint
import randomapplication.composeapp.generated.resources.text_field_email_label
import randomapplication.composeapp.generated.resources.text_field_email_placeholder
import randomapplication.composeapp.generated.resources.text_field_password_label
import randomapplication.composeapp.generated.resources.text_field_password_placeholder

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
 * В функции [LoginScreen] добавлено логирование, срабатывающее при получении данных из `Composable` [GreetingScreen]
 *
 * @param id Значение, которое передается при надатии на кнопку в экране [GreetingScreen]
 * @author Иван Корниенко*/

@Composable
fun LoginScreen(
    id: String
) {
    Scaffold(modifier = CustomModifiers.scaffoldModifier) { innerPadding ->
        BoxWithConstraints(
            modifier = CustomModifiers.constraintModifier(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            val isLandscape = maxWidth > maxHeight

            // Все `states` вынесены за пределы функций во избежания потери данных
            val emailState = rememberSaveable { mutableStateOf("") }
            val passwordState = rememberSaveable { mutableStateOf("") }
            val errorState = rememberSaveable { mutableStateOf(false) }
            val focusState = rememberSaveable { mutableStateOf(false) }

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
                        emailState = emailState,
                        passwordState = passwordState,
                        errorState = errorState,
                        focusState = focusState
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
                        emailState = emailState,
                        passwordState = passwordState,
                        errorState = errorState,
                        focusState = focusState
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
 * - [AcceptButton] - кнодка "отправки" данных
 *
 * Дополнительные `Composables`:
 * - [CompositionLocalProvider] - необходимо для прозрачной заливки при автоподстановке данных в поля ввода
 *
 * @param emailState State для поля ввода Email - [EmailTextField]
 * @param passwordState State для поля ввода Password - [PasswordTextField]
 * @param errorState State для обработки ошибок при вводе Email
 * @param focusState State для поля состояния фокуса поля ввода Email
 *
 * @author Иван Корниенко*/

@Composable
fun InputContent(
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    errorState: MutableState<Boolean>,
    focusState: MutableState<Boolean>
) {
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
                EmailTextField(emailState, errorState, focusState)

                PasswordTextField(passwordState)
            }


            AcceptButton()
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
 * Для обработки состояния фокуса на поле в [androidx.compose.ui.Modifier] прокидывается [onFocusChanged]
 *
 * `supportingText` появляется только в том случае, если при вводе допущены ошибки
 * Так как логика обработки ошибок внедрена в метод [onFocusChanged], ошибка появляется только тогда, когда уходит фокус с поля ввода
 *
 * В функции [EmailTextField] добавлено логирование, срабатывающее при получении правильной почты
 *
 * Весь текст в `label` и `placeholder` добавлен через `strings.xml` из `composeResources`
 *
 * @param emailState State для поля ввода Email - [EmailTextField]
 * @param errorState State для обработки ошибок при вводе Email
 * @param focusState State для поля состояния фокуса поля ввода Email
 *
 * @author Иван Корниенко*/

@Composable
fun EmailTextField(
    emailState: MutableState<String>,
    errorState: MutableState<Boolean>,
    focusState: MutableState<Boolean>
) {
    var email by emailState
    var isError by errorState
    var wasFocused by focusState

    // Логика обработки поля ввода: если поле пустое или не соответствует шаблону Email, прокидывается ошибка
    val emailErrorAction = { focusState: FocusState ->
        if (focusState.isFocused) {
            wasFocused = true
        } else {
            if (wasFocused) {
                val trimmedData = email.trim()
                isError = !trimmedData.isValidEmail()
            }
        }
    }

    OutlinedTextField(
        value = email,
        onValueChange = {
            email = it
            if (isError) isError = false
        },
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
            emailErrorAction(focusState)
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
 * **ВНИМАНИЕ: указанный выше функционал является крайне плохим с точки зрания безопасности и будет удален в следующем обновлении**
 *
 * Весь текст в `label` и `placeholder` добавлен через `strings.xml` из `composeResources`
 *
 * @param passwordState State для поля ввода Password - [PasswordTextField]
 *
 * @author Иван Корниенко*/

@Composable
fun PasswordTextField(passwordState: MutableState<String>) {
    var password by passwordState

    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
        },
        label = { Text(text = stringResource(Res.string.text_field_password_label)) },
        placeholder = { Text(text = stringResource(Res.string.text_field_password_placeholder)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = CustomShapes.unifiedShape,
        modifier = CustomModifiers.inputModifier.semantics { contentType = ContentType.Password },
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
 * @author Иван Корниенко*/

@Composable
fun AcceptButton() {
    Button(
        onClick = { Napier.d(tag = "Button Clicked!..") { "...but nothing happens =(" } },
        modifier = CustomModifiers.buttonModifier,
        shape = CustomShapes.unifiedShape,
        content = {
            Text(
                text = stringResource(Res.string.button_action),
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
 * [LoginScreen] обернут в [RandomAppTheme], чтобы применить цветовую палитру.
 *
 * @author Иван Корниенко*/

@Preview(showBackground = true, locale = "en")
@Composable
fun LoginScreenPreview() {
    RandomAppTheme {
        LoginScreen("")
    }
}
