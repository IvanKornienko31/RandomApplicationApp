package com.github.ivankornienko31.stepikclientapplication.routing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.ivankornienko31.stepikclientapplication.datastore.OnboardingPreferences
import com.github.ivankornienko31.stepikclientapplication.screens.greeting.presentation.GreetingScreen
import com.github.ivankornienko31.stepikclientapplication.screens.login.presentation.LoginScreen
import com.github.ivankornienko31.stepikclientapplication.screens.main.presentation.MainStepikScreen
import com.github.ivankornienko31.stepikclientapplication.themes.StepikAppTheme
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

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

@Composable
fun Router() {
    val onboardingPreferences: OnboardingPreferences = koinInject()

    val isFirstLaunch by onboardingPreferences.isFirstLaunch.collectAsStateWithLifecycle(null)
    val coroutineScope = rememberCoroutineScope()

    if (isFirstLaunch == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    StepikAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            val startDestination = if (isFirstLaunch == true) {
                GreetingScreenRoute
            } else {
                LoginScreenRoute("login")
            }

            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable<GreetingScreenRoute> {
                    GreetingScreen {
                        coroutineScope.launch {
                            onboardingPreferences.setFirstLaunchCompleted()
                        }

                        navController.navigate(
                            LoginScreenRoute(
                                "login"
                            )
                        ) {
                            popUpTo<GreetingScreenRoute> {
                                inclusive = true
                            }
                        }
                    }
                }
                composable<LoginScreenRoute> { backStackEntry ->
                    val args = backStackEntry.toRoute<LoginScreenRoute>()
                    val id = args.id

                    LoginScreen(
                        id = id,
                        onNavigateToMain = {
                            navController.navigate(MainScreenRoute) {
                                popUpTo<LoginScreenRoute> {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable<MainScreenRoute> {
                    MainStepikScreen()
                }
            }
        }
    }
}