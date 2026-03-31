package com.github.ivankornienko31.stepikclientapplication.screens.main.data

import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.StepikCourseDto
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.StepikCoursesResponse
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.StepikSearchResponse
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.toDomain
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.PaginatedResult
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.StepikCoursesRepository
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class StepikCoursesRepositoryImpl : StepikCoursesRepository {
    private val client = stepikHttpClient.client
    private val baseUrl = "https://stepik.org/api"

    override suspend fun getCourses(page: Int, pagesToLoad: Int): Result<PaginatedResult> {
        return withContext(Dispatchers.IO) {
            runCatchingCancellable {
                val allCourses = mutableListOf<StepikCourseDto>()
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
                        break
                    }

                    currentPage++
                }

                PaginatedResult(allCourses.toDomain(), hasNext, currentPage)
            }.onFailure { e ->
                Napier.e("Ошибка при загрузке курсов", e, tag = "StepikRepo")
            }
        }
    }

    override suspend fun searchCourses(query: String, page: Int): Result<PaginatedResult> {
        return withContext(Dispatchers.IO) {
            runCatchingCancellable {
                val searchResponse: StepikSearchResponse = client.get("$baseUrl/search-results") {
                    parameter("is_popular", true)
                    parameter("is_public", true)
                    parameter("page", page)
                    parameter("query", query)
                    parameter("type", "course")
                }.body()

                val hasNext = searchResponse.meta.hasNext
                val courseIds = searchResponse.searchResults.map { it.courseId }

                if (courseIds.isEmpty()) {
                    return@runCatchingCancellable PaginatedResult(emptyList(), hasNext, page + 1)
                }

                val coursesResponse: StepikCoursesResponse = client.get("$baseUrl/courses") {
                    courseIds.forEach { id ->
                        parameter("ids[]", id)
                    }
                }.body()

                val coursesMap = coursesResponse.courses.associateBy { it.id }
                val sortedCourses = courseIds.mapNotNull { coursesMap[it] }

                PaginatedResult(sortedCourses.toDomain(), hasNext, page + 1)
            }.onFailure { e ->
                if (e is CancellationException) throw e
                Napier.e("Ошибка при поиске курсов", e, tag = "StepikRepo")
            }
        }
    }
}