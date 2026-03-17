package com.github.ivankornienko31.stepikclientapplication

import androidx.compose.ui.window.ComposeUIViewController
import com.github.ivankornienko31.stepikclientapplication.datastore.OnboardingPreferences
import com.github.ivankornienko31.stepikclientapplication.datastore.createDataStore
import com.github.ivankornienko31.stepikclientapplication.routing.Router

fun MainViewController() = ComposeUIViewController {
    val dataStore = createDataStore()
    val preferences = OnboardingPreferences(dataStore)

    Router(onboardingPreferences = preferences)
}