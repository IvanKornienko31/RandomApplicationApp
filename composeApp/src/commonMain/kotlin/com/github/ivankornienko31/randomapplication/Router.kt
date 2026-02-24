package com.github.ivankornienko31.randomapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.ivankornienko31.randomapplication.ui.routes.LoginScreenRoute
import com.github.ivankornienko31.randomapplication.ui.routes.MainScreenRoute
import com.github.ivankornienko31.randomapplication.ui.screens.GreetingScreen
import com.github.ivankornienko31.randomapplication.ui.screens.LoginScreen
import com.github.ivankornienko31.randomapplication.ui.themes.RandomAppTheme

@Composable
@Preview
fun Router() {
    RandomAppTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MainScreenRoute
        ) {
            composable<MainScreenRoute> {
                GreetingScreen {
                    navController.navigate(LoginScreenRoute("login"))
                }
            }
            composable<LoginScreenRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<LoginScreenRoute>()

                val id = args.id

                LoginScreen(id = id)
            }
        }
    }
}