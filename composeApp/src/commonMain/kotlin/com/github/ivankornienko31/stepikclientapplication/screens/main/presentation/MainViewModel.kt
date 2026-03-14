package com.github.ivankornienko31.stepikclientapplication.screens.main.presentation

import androidx.lifecycle.ViewModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.PostsRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RedditPostModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.PostsUseCase
import androidx.lifecycle.viewModelScope
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.StepikCoursesRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RemoteStepikCourseModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.StepikCoursesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
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
    data class Success(
        val courses: List<RemoteStepikCourseModel>,
        val isPaginating: Boolean = false,
        val endReached: Boolean = false
    ) : CoursesUiState

    data class Error(val message: String) : CoursesUiState
    data object Empty : CoursesUiState
}

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class StepikMainViewModel : ViewModel() {
    private val repository: StepikCoursesRepository = StepikCoursesRepositoryImpl()

    private val _uiState = MutableStateFlow<CoursesUiState>(CoursesUiState.Loading)
    val uiState: StateFlow<CoursesUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val triggerSearch = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var currentPage = 1
    private var isLastPage = false

    init {
        triggerSearch.tryEmit("")

        viewModelScope.launch {
            _searchQuery
                .drop(1)
                .debounce(250L)
                .distinctUntilChanged()
                .collect { triggerSearch.tryEmit(it) }
        }

        viewModelScope.launch {
            triggerSearch.flatMapLatest { query ->
                flow {
                    emit(CoursesUiState.Loading)

                    currentPage = 1
                    isLastPage = false

                    val result = if (query.isBlank()) {
                        repository.getCourses(page = currentPage)
                    } else {
                        repository.searchCourses(query = query)
                    }

                    // Обрабатываем Result с помощью функции fold
                    val nextState = result.fold(
                        onSuccess = { paginatedData ->
                            currentPage = paginatedData.nextPage
                            isLastPage = !paginatedData.hasNext

                            if (paginatedData.courses.isEmpty()) CoursesUiState.Empty
                            else CoursesUiState.Success(
                                courses = paginatedData.courses,
                                isPaginating = false,
                                endReached = isLastPage
                            )
                        },
                        onFailure = { exception ->
                            CoursesUiState.Error(
                                message = exception.message ?: "Произошла неизвестная ошибка"
                            )
                        }
                    )

                    emit(nextState)
                }
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun loadNextPage() {
        val currentState = _uiState.value as? CoursesUiState.Success ?: return

        if (currentState.isPaginating || isLastPage) return

        viewModelScope.launch {
            _uiState.value = currentState.copy(isPaginating = true)
            val query = _searchQuery.value

            val result =
                if (query.isBlank()) repository.getCourses(page = currentPage, pagesToLoad = 1)
                else repository.searchCourses(query = query, page = currentPage)

            result.fold(
                onSuccess = { paginatedData ->
                    currentPage = paginatedData.nextPage
                    isLastPage = !paginatedData.hasNext

                    _uiState.value = currentState.copy(
                        courses = currentState.courses + paginatedData.courses,
                        isPaginating = false,
                        endReached = isLastPage
                    )
                },
                onFailure = {
                    _uiState.value = currentState.copy(isPaginating = false)
                }
            )
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun retry() {
        triggerSearch.tryEmit(_searchQuery.value)
    }
}