package com.github.ivankornienko31.stepikclientapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.ivankornienko31.stepikclientapplication.datastore.OnboardingPreferences
import com.github.ivankornienko31.stepikclientapplication.datastore.createDataStore
import com.github.ivankornienko31.stepikclientapplication.routing.Router

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val dataStore = createDataStore(applicationContext)
        val preferences = OnboardingPreferences(dataStore)

        setContent {
            Router(onboardingPreferences = preferences)
        }
    }
}
