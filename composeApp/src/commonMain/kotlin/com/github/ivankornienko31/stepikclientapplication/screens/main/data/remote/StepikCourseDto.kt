package com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote

import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.StepikCourse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StepikCourseDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("cover")
    val coursePicture: String?,
    @SerialName("display_price")
    val price: String
)

fun StepikCourseDto.toDomain(): StepikCourse {
    return StepikCourse(
        id = id,
        title = title,
        summary = summary,
        coursePicture = coursePicture,
        price = price
    )
}

fun List<StepikCourseDto>.toDomain(): List<StepikCourse> {
    return map { it.toDomain() }
}