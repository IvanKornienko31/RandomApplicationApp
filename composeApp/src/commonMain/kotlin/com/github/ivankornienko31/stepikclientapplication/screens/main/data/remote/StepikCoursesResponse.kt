package com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StepikCoursesResponse(
    @SerialName("meta")
    val meta: MetaResponse,
    @SerialName("courses")
    val courses: List<RemoteStepikCourseModel>
)