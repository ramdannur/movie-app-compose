package id.ramdannur.movieappcompose.core.domain.usecase

import id.ramdannur.movieappcompose.core.domain.model.Movie
import id.ramdannur.movieappcompose.core.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getDiscoverMovie(): Flow<UiState<List<Movie>>>
    fun getSearchMovie(keyword: String): Flow<UiState<List<Movie>>>
    fun getDetailId(id: String): Flow<UiState<Movie>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun getFavoriteMovieById(id: Int): Flow<List<Movie>>
    suspend fun setFavoriteMovie(movie: Movie, state: Boolean)
}