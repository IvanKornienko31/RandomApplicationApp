package com.github.ivankornienko31.stepikclientapplication.screens.main.presentation

import androidx.lifecycle.ViewModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.PostsRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.RedditPostModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.PostsUseCase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val postsUseCase: PostsUseCase = PostsUseCase(PostsRepositoryImpl())
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<RedditPostModel>?>(null)
    val uiState: StateFlow<List<RedditPostModel>?> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            val posts = postsUseCase()
            _uiState.update { posts }
        }
    }
}