package id.ramdannur.movieappcompose.core.domain.usecase

import id.ramdannur.movieappcompose.core.domain.model.Movie
import id.ramdannur.movieappcompose.core.domain.repository.IMovieRepository
import id.ramdannur.movieappcompose.core.ui.common.UiState
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getDiscoverMovie() = movieRepository.getDiscoverMovie()

    override fun getSearchMovie(keyword: String) = movieRepository.getSearchMovie(keyword)

    override fun getDetailId(id: String): Flow<UiState<Movie>> = movieRepository.getDetailId(id)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun getFavoriteMovieById(id: Int) = movieRepository.getFavoriteMovieById(id)

    override suspend fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)
}