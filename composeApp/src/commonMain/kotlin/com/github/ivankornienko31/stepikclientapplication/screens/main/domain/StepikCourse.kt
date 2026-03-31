package com.github.ivankornienko31.stepikclientapplication.screens.main.domain

data class StepikCourse(
    val id: Int,
    val title: String,
    val summary: String,
    val coursePicture: String?,
    val price: String
)