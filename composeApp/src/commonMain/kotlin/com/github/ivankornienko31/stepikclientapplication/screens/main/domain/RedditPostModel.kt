package com.github.ivankornienko31.stepikclientapplication.screens.main.domain

data class RedditPostModel(
    val id: String,
    val author: String,
    val subreddit: String,
    val title: String,
    val contentText: String? = null,
    val likesCount: Int,
    val commentsCount: Int,
    val hoursAgo: Int
)