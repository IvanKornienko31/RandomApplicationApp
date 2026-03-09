package com.github.ivankornienko31.stepikclientapplication.data.repositories

import com.github.ivankornienko31.stepikclientapplication.domain.models.RedditPost
import com.github.ivankornienko31.stepikclientapplication.domain.repositories.IPostsRepository
import kotlinx.coroutines.delay

class PostsRepositoryImpl : IPostsRepository {
    override suspend fun getPosts(): List<RedditPost> {
        delay(1500) // Имитация сети
        return listOf(
            RedditPost(
                id = "1",
                author = "kot_v_sapogah",
                subreddit = "r/Kotlin",
                title = "Clean Architecture is cool",
                contentText = null,
                likesCount = 1240,
                commentsCount = 85,
                hoursAgo = 2
            ),
            RedditPost(
                id = "2",
                author = "android_dev",
                subreddit = "r/androiddev",
                title = "Jetpack Compose tips",
                contentText = "Use key in LazyColumn",
                likesCount = 850,
                commentsCount = 42,
                hoursAgo = 5
            ),
            RedditPost(
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