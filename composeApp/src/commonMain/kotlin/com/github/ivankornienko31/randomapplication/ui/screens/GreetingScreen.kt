package com.github.ivankornienko31.randomapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.github.ivankornienko31.randomapplication.ui.themes.RandomAppTheme
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import randomapplication.composeapp.generated.resources.Res
import randomapplication.composeapp.generated.resources.button_action
import randomapplication.composeapp.generated.resources.main_screen_dream
import randomapplication.composeapp.generated.resources.main_screen_greeting
import randomapplication.composeapp.generated.resources.main_screen_nice
import randomapplication.composeapp.generated.resources.main_screen_picture_description

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
            contentDescription = stringResource(Res.string.main_screen_picture_description),
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
                    Text(
                        text = stringResource(Res.string.main_screen_greeting),
                        style = CustomTextStyles.headerStyle
                    )

                    HorizontalDivider(
                        thickness = CustomDimens.dividerThickness,
                        modifier = CustomModifiers.dividerModifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = stringResource(Res.string.main_screen_nice),
                        style = CustomTextStyles.mainTextStyle
                    )

                    Text(
                        text = stringResource(Res.string.main_screen_dream),
                        style = CustomTextStyles.mainTextStyle
                    )
                }
            }
            Box {
                Button(
                    onClick = navigateToLogin,
                    content = {
                        Text(
                            text = stringResource(Res.string.button_action),
                            style = CustomTextStyles.buttonFontStyle
                        )
                    },
                    modifier = CustomModifiers.buttonModifier
                )
            }
        }
    }
}

@Preview(showBackground = true, locale = "en")
@Composable
fun GreetingScreenPreview() {
    RandomAppTheme {
        GreetingScreen {}
    }
}
