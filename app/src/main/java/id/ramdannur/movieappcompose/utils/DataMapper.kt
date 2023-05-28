package id.ramdannur.movieappcompose.utils

import id.ramdannur.movieappcompose.data.source.local.entity.FavoriteEntity
import id.ramdannur.movieappcompose.data.source.local.entity.MovieEntity
import id.ramdannur.movieappcompose.data.source.remote.response.MovieResponse
import id.ramdannur.movieappcompose.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapResponsesToDomain(input: List<MovieResponse>): List<Movie> {
        val movieList = ArrayList<Movie>()
        input.map {
            movieList.add(movieResponseToDomain(it))
        }
        return movieList
    }

    fun movieResponseToDomain(it: MovieResponse): Movie {
        return Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            backdropPath = it.backdropPath,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            voteAverage = it.voteAverage,
        )
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
            )
        }

    fun mapFavoriteEntitiesToDomain(input: List<FavoriteEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
            )
        }

    fun mapDomainToFavoriteEntity(input: Movie) = FavoriteEntity(
        id = input.id,
        title = input.title ?: "",
        overview = input.overview ?: "",
        backdropPath = input.backdropPath,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate ?: "",
        voteAverage = input.voteAverage ?: 0.0,
    )
}