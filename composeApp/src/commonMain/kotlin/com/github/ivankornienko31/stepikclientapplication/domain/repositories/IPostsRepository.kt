package com.github.ivankornienko31.stepikclientapplication.domain.repositories

import com.github.ivankornienko31.stepikclientapplication.domain.models.RedditPost

interface IPostsRepository {
    suspend fun getPosts(): List<RedditPost>
}