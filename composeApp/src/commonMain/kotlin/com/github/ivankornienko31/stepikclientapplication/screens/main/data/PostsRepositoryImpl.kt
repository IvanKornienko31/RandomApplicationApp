package com.github.ivankornienko31.stepikclientapplication.screens.main.data

import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RedditPostModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.PostsRepository
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RemoteStepikCourseModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.StepikCoursesResponse
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.StepikSearchResponse
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.StepikCoursesRepository
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.delay

@Deprecated(
    message = "This interface will be replaced by Stepik alternative",
    replaceWith = ReplaceWith(
        expression = "StepikCoursesRepositoryImpl"
    )
)
class PostsRepositoryImpl : PostsRepository {
    override suspend fun getPosts(): List<RedditPostModel> {
        delay(1500) // Имитация сети

        return listOf(
            RedditPostModel(
                id = "1",
                author = "kot_v_sapogah",
                subreddit = "r/Kotlin",
                title = "Clean Architecture is cool",
                contentText = null,
                likesCount = 1240,
                commentsCount = 85,
                hoursAgo = 2
            ),
            RedditPostModel(
                id = "2",
                author = "android_dev",
                subreddit = "r/androiddev",
                title = "Jetpack Compose tips",
                contentText = "Use key in LazyColumn",
                likesCount = 850,
                commentsCount = 42,
                hoursAgo = 5
            ),
            RedditPostModel(
                id = "3",
                author = "memelord",
                subreddit = "r/funny",
                title = "When the build fails",
                contentText = null,
                likesCount = 5600,
                commentsCount = 230,
                hoursAgo = 1
            )
        )
    }
}

class StepikCoursesRepositoryImpl : StepikCoursesRepository {
    private val client = StepikHttpClient.client
    private val baseUrl = "https://stepik.org/api"

    override suspend fun getCourses(page: Int): Result<List<RemoteStepikCourseModel>> {
        return runCatching {
            val response: StepikCoursesResponse = client.get("$baseUrl/courses") {
                parameter("page", page)
            }.body()

            response.courses
        }.onFailure { e ->
            Napier.e("Ошибка при загрузке курсов", e, tag = "StepikRepo")
        }
    }

    override suspend fun searchCourses(query: String, page: Int): Result<List<RemoteStepikCourseModel>> {
        return runCatching {
            val searchResponse: StepikSearchResponse = client.get("$baseUrl/search-results") {
                parameter("is_popular", true)
                parameter("is_public", true)
                parameter("page", page)
                parameter("query", query)
                parameter("type", "course")
            }.body()

            val courseIds = searchResponse.searchResults.map { it.courseId }

            if (courseIds.isEmpty()) {
                return@runCatching emptyList()
            }

            val coursesResponse: StepikCoursesResponse = client.get("$baseUrl/courses") {
                courseIds.forEach { id ->
                    parameter("ids[]", id)
                }
            }.body()

            val coursesMap = coursesResponse.courses.associateBy { it.id }
            courseIds.mapNotNull { id -> coursesMap[id] }
        }.onFailure { e ->
            Napier.e("Ошибка при поиске курсов", e, tag = "StepikRepo")
        }
    }
}