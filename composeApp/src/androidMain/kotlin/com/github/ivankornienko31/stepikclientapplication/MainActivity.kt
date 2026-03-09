package com.github.ivankornienko31.stepikclientapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.ivankornienko31.stepikclientapplication.routing.Router

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Router()
        }
    }
}

@Preview
@Composable
fun RouterAndroidPreview() {
    Router()
}