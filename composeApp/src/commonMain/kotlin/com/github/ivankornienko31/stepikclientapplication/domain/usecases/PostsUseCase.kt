package com.github.ivankornienko31.stepikclientapplication.domain.usecases

import com.github.ivankornienko31.stepikclientapplication.domain.models.RedditPost
import com.github.ivankornienko31.stepikclientapplication.domain.repositories.IPostsRepository

class GetPostsUseCase(private val repository: IPostsRepository) {
    suspend operator fun invoke(): List<RedditPost> {
        return repository.getPosts().sortedBy { it.hoursAgo }
    }
}