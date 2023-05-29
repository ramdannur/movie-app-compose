package id.ramdannur.movieappcompose.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ramdannur.movieappcompose.core.domain.model.Movie
import id.ramdannur.movieappcompose.core.domain.usecase.MovieUseCase
import id.ramdannur.movieappcompose.core.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading())
    val uiState: StateFlow<UiState<List<Movie>>>
        get() = _uiState

    fun getFavoriteMovies() {
        viewModelScope.launch {
            movieUseCase.getFavoriteMovie()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}