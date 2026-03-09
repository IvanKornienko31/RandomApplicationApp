package com.github.ivankornienko31.stepikclientapplication.presentation.screens.greeting

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
import com.github.ivankornienko31.stepikclientapplication.presentation.themes.CustomDimens
import com.github.ivankornienko31.stepikclientapplication.presentation.themes.CustomModifiers
import com.github.ivankornienko31.stepikclientapplication.presentation.themes.CustomTextStyles
import com.github.ivankornienko31.stepikclientapplication.presentation.themes.RandomAppTheme
import com.skydoves.compose.stability.runtime.TraceRecomposition
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import stepikclientapplication.composeapp.generated.resources.Res
import stepikclientapplication.composeapp.generated.resources.greeting_button_action
import stepikclientapplication.composeapp.generated.resources.greeting_screen_dream
import stepikclientapplication.composeapp.generated.resources.greeting_screen_greeting
import stepikclientapplication.composeapp.generated.resources.greeting_screen_nice
import stepikclientapplication.composeapp.generated.resources.greeting_screen_picture_description

/**
 * [GreetingScreen] является стартовым экраном приложения
 *
 * Общие `Composables`:
 * - [AsyncImage] - предоставлено Coil
 * - [Text]
 * - [HorizontalDivider] - горизонтальная черта под строкой "Привет"
 * - [Button]
 *
 * Дополнительные `Composables`:
 * - [BoxWithConstraints] - необходимо для адаптации `Composable` при повороте экрана
 *
 * Весь текст добавлен через `strings.xml` из `composeResources`
 *
 * @author Иван Корниенко
 * @param navigateToLogin Callback для навигации к экрану [com.github.ivankornienko31.stepikclientapplication.presentation.screens.login.LoginScreen]*/

@Composable
fun GreetingScreen(navigateToLogin: () -> Unit) {
    Scaffold(modifier = CustomModifiers.scaffoldModifier) { innerPadding ->
        BoxWithConstraints(
            modifier = CustomModifiers.Companion.constraintModifier(innerPadding),
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

                    BodyContent(
                        navigateToLogin
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
                    LoadedImage()

                    BodyContent(
                        navigateToLogin
                    )
                }
            }
        }
    }
}

/**
 * [LoadedImage] является контейнером картинки, загружаемой через Coil.
 *
 * При повороте телефона в горизонтальное положение картинка перемещается в левую сторону экрана
 *
 * Общие `Composables`:
 * - [AsyncImage] - предоставлено Coil
 *
 * В параметр [onSuccess] элемента [AsyncImage] добавлено логирование, срабатывающее при успешной загрузке изображения
 *
 * @author Иван Корниенко*/

@TraceRecomposition
@Composable
fun LoadedImage() {
    Box(
        modifier = CustomModifiers.pictureModifier
    ) {
        AsyncImage(
            model = "https://redditinc.com/hs-fs/hubfs/Reddit%20Inc/Content/Brand%20Page/Reddit_Logo.png",
            onSuccess = { Napier.d(tag = "Image state") { "Image was loaded successfully" } },
            contentDescription = stringResource(Res.string.greeting_screen_picture_description),
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * [BodyContent] является `Composable`, содержащим все остальные базовые элементы: текст, кнопки и горизонтальная черта.
 *
 * Необходимость в этом `Composable` заключается в том, чтобы разграничить картинку от остального контента, если экран находится в горизонтальном положении
 *
 * При повороте телефона в горизонтальное положение `Composable` перемещается в правую сторону экрана
 *
 * Общие `Composables`:
 * - [Text]
 * - [HorizontalDivider] - горизонтальная черта под строкой "Привет"
 * - [Button]
 *
 * @param navigateToLogin Callback для навигации к экрану [com.github.ivankornienko31.stepikclientapplication.presentation.screens.login.LoginScreen]
 *
 * @author Иван Корниенко*/

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
                        text = stringResource(Res.string.greeting_screen_greeting),
                        style = CustomTextStyles.headerStyle
                    )

                    HorizontalDivider(
                        thickness = CustomDimens.dividerThickness,
                        modifier = CustomModifiers.dividerModifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = stringResource(Res.string.greeting_screen_nice),
                        style = CustomTextStyles.mainTextStyle
                    )

                    Text(
                        text = stringResource(Res.string.greeting_screen_dream),
                        style = CustomTextStyles.mainTextStyle
                    )
                }
            }
            Box {
                Button(
                    onClick = navigateToLogin,
                    content = {
                        Text(
                            text = stringResource(Res.string.greeting_button_action),
                            style = CustomTextStyles.buttonFontStyle
                        )
                    },
                    modifier = CustomModifiers.buttonModifier
                )
            }
        }
    }
}

/**
 * Превью для свёрстанного экрана [com.github.ivankornienko31.stepikclientapplication.presentation.screens.greeting.GreetingScreen].
 *
 * По умолчанию стоит английская локаль, также показывается фон приложения.
 *
 * [com.github.ivankornienko31.stepikclientapplication.presentation.screens.greeting.GreetingScreen] обернут в [com.github.ivankornienko31.stepikclientapplication.presentation.themes.RandomAppTheme], чтобы применить цветовую палитру.
 *
 * @author Иван Корниенко*/

@Preview(showBackground = true, locale = "en")
@Composable
fun GreetingScreenPreview() {
    RandomAppTheme {
        GreetingScreen {}
    }
}
