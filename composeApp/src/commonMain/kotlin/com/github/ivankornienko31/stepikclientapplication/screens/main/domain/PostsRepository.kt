package com.github.ivankornienko31.stepikclientapplication.screens.main.domain

import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RedditPostModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RemoteStepikCourseModel

@Deprecated(
    message = "This interface will be replaced by Stepik alternative",
    replaceWith = ReplaceWith(
        expression = "StepikCoursesRepository"
    )
)
interface PostsRepository {
    suspend fun getPosts(): List<RedditPostModel>
}

data class PaginatedResult(
    val courses: List<RemoteStepikCourseModel>,
    val hasNext: Boolean,
    val nextPage: Int
)

interface StepikCoursesRepository {
    suspend fun getCourses(page: Int = 1, pagesToLoad: Int = 2): Result<PaginatedResult>

    suspend fun searchCourses(query: String, page: Int = 1): Result<PaginatedResult>
}