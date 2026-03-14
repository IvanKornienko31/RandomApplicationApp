package com.github.ivankornienko31.stepikclientapplication.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.ivankornienko31.stepikclientapplication.screens.greeting.presentation.GreetingScreen
import com.github.ivankornienko31.stepikclientapplication.screens.login.presentation.LoginScreen
import com.github.ivankornienko31.stepikclientapplication.screens.main.presentation.MainScreen
import com.github.ivankornienko31.stepikclientapplication.screens.main.presentation.MainStepikScreen
import com.github.ivankornienko31.stepikclientapplication.screens.main.presentation.StepikMainViewModel
import com.github.ivankornienko31.stepikclientapplication.themes.StepikAppTheme
import com.skydoves.compose.stability.runtime.TraceRecomposition

/**
 * Функция [Router] отображает содержимое приложение с кастомной темой.
 *
 * Добавлена поддержка Jetpack Compose Navigation через [Serializable]
 *
 * Чтобы посмотреть объявленные Serializable, см. ./ui/routes/ScreenRoutes.kt
 *
 * - [GreetingScreen] - Стартовый Composable, содержащий Coil-картинку
 *
 * - [LoginScreen] - Composable, содержащий поля ввода и кнопку
 *
 * @author Иван Корниенко
 */

@TraceRecomposition
@Composable
@Preview
fun Router() {
    StepikAppTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = GreetingScreenRoute
        ) {
            composable<GreetingScreenRoute> {
                GreetingScreen {
                    navController.navigate(
                        LoginScreenRoute(
                            "login"
                        )
                    )
                }
            }
            composable<LoginScreenRoute> { backStackEntry ->
                val args =
                    backStackEntry.toRoute<LoginScreenRoute>()

                val id = args.id

                LoginScreen(
                    id = id,
                    onNavigateToMain = {
                        navController.navigate(MainScreenRoute) {
                            popUpTo<GreetingScreenRoute> {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<MainScreenRoute> {
//                MainScreen()
                MainStepikScreen()
            }
        }
    }
}