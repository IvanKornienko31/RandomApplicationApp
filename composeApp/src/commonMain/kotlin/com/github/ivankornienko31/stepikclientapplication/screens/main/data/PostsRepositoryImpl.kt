package com.github.ivankornienko31.stepikclientapplication.screens.main.data

import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RedditPostModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.PostsRepository
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RemoteStepikCourseModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.StepikCoursesResponse
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.StepikSearchResponse
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.PaginatedResult
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

    override suspend fun getCourses(page: Int, pagesToLoad: Int): Result<PaginatedResult> {
        return runCatching {
            val allCourses = mutableListOf<RemoteStepikCourseModel>()
            var currentPage = page
            var validPagesLoad = 0
            var hasNext = false

            while (validPagesLoad < pagesToLoad) {
                val response: StepikCoursesResponse = client.get("$baseUrl/courses") {
                    parameter("page", currentPage)
                }.body()

                if (response.courses.isNotEmpty()) {
                    allCourses.addAll(response.courses)
                    validPagesLoad++
                    Napier.d(tag = "StepikRepo") { "Загружена страница $currentPage. Всего курсов пока: ${allCourses.size}" }
                } else {
                    Napier.w(tag = "StepikRepo") { "Страница $currentPage пустая, пропускаем и ищем дальше..." }
                }

                hasNext = response.meta.hasNext
                if (!response.meta.hasNext) {
                    Napier.d(tag = "StepikRepo") { "Достигнут конец списка курсов на Stepik. Остановка." }
                    break // Выходим из цикла, так как следующих страниц не существует
                }

                currentPage++
            }

            PaginatedResult(allCourses, hasNext, currentPage)
        }.onFailure { e ->
            Napier.e("Ошибка при загрузке курсов", e, tag = "StepikRepo")
        }
    }

    override suspend fun searchCourses(query: String, page: Int): Result<PaginatedResult> {
        return runCatching {
            val searchResponse: StepikSearchResponse = client.get("$baseUrl/search-results") {
                parameter("is_popular", true)
                parameter("is_public", true)
                parameter("page", page)
                parameter("query", query)
                parameter("type", "course")
            }.body()

            var hasNext = searchResponse.meta.hasNext
            val courseIds = searchResponse.searchResults.map { it.courseId }

            if (courseIds.isEmpty()) {
                return@runCatching PaginatedResult(emptyList(), hasNext, page + 1)
            }

            val coursesResponse: StepikCoursesResponse = client.get("$baseUrl/courses") {
                courseIds.forEach { id ->
                    parameter("ids[]", id)
                }
            }.body()

            val coursesMap = coursesResponse.courses.associateBy { it.id }
            val sortedCourses = courseIds.mapNotNull { coursesMap[it] }

            PaginatedResult(sortedCourses, hasNext, page + 1)
        }.onFailure { e ->
            Napier.e("Ошибка при поиске курсов", e, tag = "StepikRepo")
        }
    }
}