package id.ramdannur.movieappcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ramdannur.movieappcompose.data.MovieRepository
import id.ramdannur.movieappcompose.model.Movie
import id.ramdannur.movieappcompose.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Movie>> =
        MutableStateFlow(UiState.Loading())
    val uiState: StateFlow<UiState<Movie>>
        get() = _uiState

    private val _isFavorite = MutableStateFlow<Boolean?>(null)
    val isFavorite: StateFlow<Boolean?> = _isFavorite

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun getRewardById(movieId: Long) {
        viewModelScope.launch {
            repository.getDetailId(movieId.toString())
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) {
        runBlocking {
            withContext(Dispatchers.IO) {
                repository.setFavoriteMovie(movie, newStatus)

                _isFavorite.emit(newStatus)

                // Show a success message
                _message.value = if (newStatus) "Added to Favorites" else "Removed from Favorites"
            }
        }
    }

    fun checkFavoriteStatus(id: Long) {
        viewModelScope.launch {
            val favoriteItem = repository.getFavoriteMovieById(id.toInt()).firstOrNull()

            _isFavorite.value = !favoriteItem.isNullOrEmpty()
        }
    }
}