package com.github.ivankornienko31.stepikclientapplication.screens.main.domain

interface PostsRepository {
    suspend fun getPosts(): List<RedditPostModel>
}