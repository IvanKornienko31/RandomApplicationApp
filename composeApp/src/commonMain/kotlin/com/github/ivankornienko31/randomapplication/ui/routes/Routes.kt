package com.github.ivankornienko31.randomapplication.ui.routes

import kotlinx.serialization.Serializable

@Serializable
object MainScreenRoute

@Serializable
data class LoginScreenRoute(val id: String)
