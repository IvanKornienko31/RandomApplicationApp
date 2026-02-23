package com.github.ivankornienko31.randomapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.github.ivankornienko31.randomapplication.extensions.isValidEmail
import com.github.ivankornienko31.randomapplication.ui.themes.CustomDimens
import com.github.ivankornienko31.randomapplication.ui.themes.CustomModifiers
import com.github.ivankornienko31.randomapplication.ui.themes.CustomShapes
import com.github.ivankornienko31.randomapplication.ui.themes.CustomTextStyles
import io.github.aakira.napier.Napier

@Composable
fun LoginScreen() {
    Scaffold(modifier = CustomModifiers.scaffoldModifier) { innerPadding ->
        BoxWithConstraints (
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
                    HelperText()

                    InputContent()
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

                    InputContent()
                }
            }
        }
    }
}

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
            Text("Вход", style = CustomTextStyles.headerStyle)

            Text(
                "Пожалуйста, введите данные для входа",
                style = CustomTextStyles.mainTextStyle
            )
        }
    }
}


@Composable
fun InputContent() {
    Box {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = CustomDimens.spaceBetweenElements,
                alignment = Alignment.CenterVertically
            )
        ) {
            EmailTextField()

            PasswordTextField()

            AcceptButton()
        }
    }
}

@Composable
fun EmailTextField() {
    var email by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = email,
        onValueChange = {
            email = it
            val trimmed = it.trim()
            isError = !trimmed.isValidEmail()
        },
        isError = isError,
        supportingText = if (isError) {
            {
                Text(
                    if (email.trim().isEmpty()) "Это поле не может быть пустым!"
                    else "Пожалуйста, введите корректный email-адрес"
                )
            }
        } else null,
        label = { Text("Email") },
        placeholder = { Text("Введите email") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        shape = CustomShapes.unifiedShape,
        modifier = CustomModifiers.inputModifier,
    )

    LaunchedEffect(isError, email) {
        if (!isError && email.trim().isValidEmail()) {
            Napier.d { "Congratulations! You've got a correct email: $email" }
        }
    }
}

@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
        },
        label = { Text("Пароль") },
        placeholder = { Text("Введите пароль") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = CustomShapes.unifiedShape,
        modifier = CustomModifiers.inputModifier,
        visualTransformation = PasswordVisualTransformation()
    )

    LaunchedEffect(password) {
        Napier.d(tag = "New Value Detected") { "Current value is $password" }
    }
}

@Composable
fun AcceptButton() {
    Button(
        onClick = { Napier.d(tag = "Button Clicked!..") { "...but nothing happens =(" } },
        modifier = CustomModifiers.buttonModifier,
        shape = CustomShapes.unifiedShape,
        content = { Text("Войти", style = CustomTextStyles.buttonFontStyle) },
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
