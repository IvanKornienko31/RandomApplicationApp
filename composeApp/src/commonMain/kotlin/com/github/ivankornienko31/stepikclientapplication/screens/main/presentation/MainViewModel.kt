package com.github.ivankornienko31.stepikclientapplication.screens.main.presentation

import androidx.lifecycle.ViewModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.PostsRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RedditPostModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.PostsUseCase
import androidx.lifecycle.viewModelScope
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.StepikCoursesRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RemoteStepikCourseModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.StepikCoursesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Deprecated(
    message = "This interface will be replaced by Stepik alternative",
    replaceWith = ReplaceWith(
        expression = "StepikMainViewModel"
    )
)
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

sealed interface CoursesUiState {
    data object Loading : CoursesUiState
    data class Success(val courses: List<RemoteStepikCourseModel>) : CoursesUiState
    data class Error(val message: String) : CoursesUiState
}

class StepikMainViewModel: ViewModel() {
    private val repository: StepikCoursesRepository = StepikCoursesRepositoryImpl()

    private val _uiState = MutableStateFlow<CoursesUiState>(CoursesUiState.Loading)
    val uiState: StateFlow<CoursesUiState> = _uiState.asStateFlow()

    init {
        // При создании ViewModel сразу грузим данные
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _uiState.value = CoursesUiState.Loading // Показываем крутилку загрузки

            // Вызываем репозиторий. Он возвращает Result
            val result = repository.getCourses(page = 1)

            // Обрабатываем Result с помощью функции fold
            result.fold(
                onSuccess = { courses ->
                    _uiState.value = CoursesUiState.Success(courses)
                },
                onFailure = { exception ->
                    _uiState.value = CoursesUiState.Error(
                        message = exception.message ?: "Произошла неизвестная ошибка"
                    )
                }
            )
        }
    }
}