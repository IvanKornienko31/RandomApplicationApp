package com.github.ivankornienko31.randomapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.github.ivankornienko31.randomapplication.ui.themes.CustomDimens
import com.github.ivankornienko31.randomapplication.ui.themes.CustomModifiers
import com.github.ivankornienko31.randomapplication.ui.themes.CustomTextStyles
import io.github.aakira.napier.Napier

@Composable
fun GreetingScreen(navigateToLogin: () -> Unit) {
    Scaffold(modifier = CustomModifiers.scaffoldModifier) { innerPadding ->
        BoxWithConstraints(
            modifier = CustomModifiers.constraintModifier(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            val isLandscape = maxWidth > maxHeight

            if (isLandscape) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = CustomDimens.spaceBetweenElements,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = CustomModifiers.flexModifier
                ) {
                    LoadedImage()

                    BodyContent(navigateToLogin)
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
                    LoadedImage()

                    BodyContent(navigateToLogin)
                }
            }
        }
    }
}

@Composable
fun LoadedImage() {
    Box(
        modifier = CustomModifiers.pictureModifier
    ) {
        AsyncImage(
            model = "https://i.pinimg.com/736x/3d/6d/df/3d6ddfdbf109791c85c9facf5286d741.jpg",
            onSuccess = { Napier.d(tag = "Image state") { "Image was loaded successfully" } },
            contentDescription = "Falling Star",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun BodyContent(navigateToLogin: () -> Unit) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = CustomModifiers.flexModifier
        ) {
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = CustomDimens.spaceBetweenElements,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Text("Привет!", style = CustomTextStyles.headerStyle)

                    HorizontalDivider(
                        thickness = CustomDimens.dividerThickness,
                        modifier = CustomModifiers.dividerModifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        "Рад видеть тебя снова =)",
                        style = CustomTextStyles.mainTextStyle
                    )

                    Text(
                        "Нажми на кнопку, чтобы войти в свой аккаунт и помечтать",
                        style = CustomTextStyles.mainTextStyle
                    )
                }
            }
            Box {
                Button(
                    onClick = navigateToLogin,
                    content = {
                        Text(
                            "Войти",
                            style = CustomTextStyles.buttonFontStyle
                        )
                    },
                    modifier = CustomModifiers.buttonModifier.width(CustomDimens.greetingButtonWidth)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    GreetingScreen { }
}
