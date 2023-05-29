package id.ramdannur.movieappcompose.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ramdannur.movieappcompose.core.domain.model.Movie
import id.ramdannur.movieappcompose.core.domain.usecase.MovieUseCase
import id.ramdannur.movieappcompose.core.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading())
    val uiState: StateFlow<UiState<List<Movie>>>
        get() = _uiState

    private val _keyword = mutableStateOf("")
    val keyword: String
        get() = _keyword.value

    fun setKeyword(newInput: String) {
        _keyword.value = newInput
    }

    fun getAllMovies() {
        viewModelScope.launch {
            movieUseCase.getDiscoverMovie()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun searchMovie(keyword: String) {
        viewModelScope.launch {
            movieUseCase.getSearchMovie(keyword.trim())
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = it
                }
        }
    }
}