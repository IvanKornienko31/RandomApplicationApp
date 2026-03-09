package com.github.ivankornienko31.randomapplication.domain.repositories

import com.github.ivankornienko31.randomapplication.domain.models.RedditPost

interface IPostsRepository {
    suspend fun getPosts(): List<RedditPost>
}