package com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultItem(
    @SerialName("course")
    val courseId: Int // Нам нужен только ID курса для последующего запроса
)
