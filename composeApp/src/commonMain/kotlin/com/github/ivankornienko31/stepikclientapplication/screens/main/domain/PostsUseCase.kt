package com.github.ivankornienko31.stepikclientapplication.screens.main.domain

class PostsUseCase(private val repository: PostsRepository) {
    suspend operator fun invoke(): List<RedditPostModel> {
        return repository.getPosts().sortedBy { it.hoursAgo }
    }
}