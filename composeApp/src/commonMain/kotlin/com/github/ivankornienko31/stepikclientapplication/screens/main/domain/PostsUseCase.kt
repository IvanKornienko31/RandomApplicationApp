package com.github.ivankornienko31.stepikclientapplication.screens.main.domain

import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RedditPostModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RemoteStepikCourseModel

@Deprecated(
    message = "This interface will be replaced by Stepik alternative",
    replaceWith = ReplaceWith(
        expression = "StepikCoursesModel"
    )
)
class PostsUseCase(private val repository: PostsRepository) {
    suspend operator fun invoke(): List<RedditPostModel> {
        return repository.getPosts().sortedBy { it.hoursAgo }
    }
}

class CoursesUseCase(private val repository: StepikCoursesRepository) {
    suspend operator fun invoke(): List<RemoteStepikCourseModel> {
        return repository.getCourses()
    }
}