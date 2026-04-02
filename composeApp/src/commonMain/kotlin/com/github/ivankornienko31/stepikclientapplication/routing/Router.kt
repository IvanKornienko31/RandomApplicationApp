package com.github.ivankornienko31.stepikclientapplication.routing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.ivankornienko31.stepikclientapplication.datastore.AuthPreferences
import com.github.ivankornienko31.stepikclientapplication.datastore.OnboardingPreferences
import com.github.ivankornienko31.stepikclientapplication.screens.auth.presentation.AuthScreen
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
    val authPreferences: AuthPreferences = koinInject()

    val isFirstLaunch by onboardingPreferences.isFirstLaunch.collectAsStateWithLifecycle(null)
    val isLoggedIn by authPreferences.isLoggedIn.collectAsStateWithLifecycle(null)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    if (isFirstLaunch == null || isLoggedIn == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == false && navController.currentDestination?.route != LoginScreenRoute::class.qualifiedName && isFirstLaunch == false) {
            navController.navigate(LoginScreenRoute("Login")) {
                popUpTo(0)
            }
        }
    }

    StepikAppTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {

        val startDestination: Any = when {
            isFirstLaunch == true -> GreetingScreenRoute
            isLoggedIn == true -> MainScreenRoute
            else -> LoginScreenRoute("Login")
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

                    navController.navigate(LoginScreenRoute("Login")) {
                        popUpTo<GreetingScreenRoute> { inclusive = true }
                    }
                }
            }
            composable<LoginScreenRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<LoginScreenRoute>()
                val id = args.id

                LoginScreen(
                    id = id,
                    onNavigateToAuth = {
                        navController.navigate(AuthRoute)
                    }
                )
            }
            composable<AuthRoute> {
                AuthScreen(
                    onNavigateToMain = {
                        navController.navigate(MainScreenRoute) {
                            popUpTo<AuthRoute> {
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
//        }
    }
}