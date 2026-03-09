package com.github.ivankornienko31.stepikclientapplication.screens.main.data

import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.RedditPostModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.PostsRepository
import kotlinx.coroutines.delay

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