package com.github.ivankornienko31.stepikclientapplication

import androidx.compose.ui.window.ComposeUIViewController
import com.github.ivankornienko31.stepikclientapplication.routing.Router

fun MainViewController() = ComposeUIViewController { Router() }