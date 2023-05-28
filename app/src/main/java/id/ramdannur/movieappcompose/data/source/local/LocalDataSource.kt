package id.ramdannur.movieappcompose.data.source.local

import id.ramdannur.movieappcompose.data.source.local.entity.FavoriteEntity
import id.ramdannur.movieappcompose.data.source.local.entity.MovieEntity
import id.ramdannur.movieappcompose.data.source.local.room.FavoriteDao
import id.ramdannur.movieappcompose.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao, private val favoriteDao: FavoriteDao) {
    fun getDiscoverMovie(): Flow<List<MovieEntity>> = movieDao.getDiscoverMovie()

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = favoriteDao.getFavoriteMovie()

    fun getFavoriteMovieById(id: Int): Flow<List<FavoriteEntity>> =
        favoriteDao.findFavoriteWithId(id)

    suspend fun insertMovie(movieList: List<MovieEntity>) =
        movieDao.insertMovie(movieList)

    suspend fun setFavoriteMovie(movie: FavoriteEntity, newState: Boolean) {
        if (newState) {
            favoriteDao.insertFavorite(listOf(movie))
        } else {
            favoriteDao.delete(movie)
        }
    }
}