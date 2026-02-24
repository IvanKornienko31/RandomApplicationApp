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
import com.github.ivankornienko31.randomapplication.ui.themes.*
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import randomapplication.composeapp.generated.resources.Res
import randomapplication.composeapp.generated.resources.*

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

@Composable
fun EmailTextField(
    emailState: MutableState<String>,
    errorState: MutableState<Boolean>,
    focusState: MutableState<Boolean>
) {
    var email by emailState
    var isError by errorState
    var wasFocused by focusState

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
            if (focusState.isFocused) {
                wasFocused = true
            } else {
                if (wasFocused) {
                    val trimmedData = email.trim()
                    isError = !trimmedData.isValidEmail()
                }
            }
        },
    )

    LaunchedEffect(isError, email) {
        if (!isError && email.trim().isValidEmail()) {
            Napier.d { "Congratulations! You've got a correct email: $email" }
        }
    }
}

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

@Composable
fun AcceptButton() {
    Button(
        onClick = { Napier.d(tag = "Button Clicked!..") { "...but nothing happens =(" } },
        modifier = CustomModifiers.buttonModifier,
        shape = CustomShapes.unifiedShape,
        content = { Text(text = stringResource(Res.string.button_action), style = CustomTextStyles.buttonFontStyle) },
    )
}

@Preview(showBackground = true, locale = "en", apiLevel = 31)
@Composable
fun LoginScreenPreview() {
    RandomAppTheme {
        LoginScreen("")
    }
}
