package com.github.ivankornienko31.stepikclientapplication.screens.course.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CourseInformationScreen(
    viewModel: CourseInformationViewModel = viewModel { CourseInformationViewModel() }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
}