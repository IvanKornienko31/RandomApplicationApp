package com.github.ivankornienko31.stepikclientapplication

import androidx.compose.ui.window.ComposeUIViewController
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.ivankornienko31.stepikclientapplication.datastore.OnboardingPreferences
import com.github.ivankornienko31.stepikclientapplication.datastore.createDataStore
import com.github.ivankornienko31.stepikclientapplication.di.initKoin
import com.github.ivankornienko31.stepikclientapplication.routing.Router
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController(
    configure = {
        val platformModule = module {
            val dataStore = createDataStore()
            single<DataStore<Preferences>> { dataStore }
        }
        initKoin(platformDependencies = platformModule)
    }
) {
    Router()
}