package com.github.ivankornienko31.stepikclientapplication.routing

import kotlinx.serialization.Serializable

/**
 * В данном файле указаны все Serializables для навигации по Composables
 *
 * [MainScreenRoute] является реализацией экрана `GreetingScreen()`
 *
 * [LoginScreenRoute] является реализацией экрана `LoginScreen()`
 *
 * @author Иван Корниенко*/

@Serializable
object GreetingScreenRoute

@Serializable
data class LoginScreenRoute(val id: String)

@Serializable
object MainScreenRoute
