package com.github.ivankornienko31.stepikclientapplication.presentation.screens.main

import androidx.lifecycle.ViewModel
import com.github.ivankornienko31.stepikclientapplication.data.repositories.PostsRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.domain.models.RedditPost
import com.github.ivankornienko31.stepikclientapplication.domain.usecases.GetPostsUseCase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPostsUseCase: GetPostsUseCase = GetPostsUseCase(PostsRepositoryImpl())
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<RedditPost>?>(null)
    val uiState: StateFlow<List<RedditPost>?> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            val posts = getPostsUseCase()
            _uiState.update { posts }
        }
    }
}