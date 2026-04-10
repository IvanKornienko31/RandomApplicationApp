package com.github.ivankornienko31.stepikclientapplication.screens.main.domain

data class PaginatedResult(
    val courses: List<StepikCourse>,
    val hasNext: Boolean,
    val nextPage: Int
)

interface StepikCoursesRepository {
    suspend fun getCourses(page: Int = 1, pagesToLoad: Int = 2): Result<PaginatedResult>

    suspend fun searchCourses(query: String, page: Int = 1): Result<PaginatedResult>
}