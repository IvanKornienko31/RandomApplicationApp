package com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteStepikCourseModel(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("cover")
    val coursePicture: String,
    @SerialName("display_price")
    val price: String
)