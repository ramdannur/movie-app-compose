package id.ramdannur.movieappcompose.core.data

import id.ramdannur.movieappcompose.core.data.source.local.LocalDataSource
import id.ramdannur.movieappcompose.core.data.source.remote.RemoteDataSource
import id.ramdannur.movieappcompose.core.data.source.remote.network.ApiResponse
import id.ramdannur.movieappcompose.core.data.source.remote.response.MovieResponse
import id.ramdannur.movieappcompose.core.domain.model.Movie
import id.ramdannur.movieappcompose.core.domain.repository.IMovieRepository
import id.ramdannur.movieappcompose.core.ui.common.UiState
import id.ramdannur.movieappcompose.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {
    override fun getDiscoverMovie(): Flow<UiState<List<Movie>>> =
        object :
            NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getDiscoverMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getDiscoverMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }

            override fun loadFromAPI(data: List<MovieResponse>): Flow<List<Movie>> = flow {
                emit(DataMapper.mapResponsesToDomain(data))
            }
        }.asFlow()

    override fun getSearchMovie(keyword: String): Flow<UiState<List<Movie>>> =
        object :
            NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> = flow { emit(listOf()) }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getSearchMovie(keyword)

            override suspend fun saveCallResult(data: List<MovieResponse>) {}

            override fun loadFromAPI(data: List<MovieResponse>): Flow<List<Movie>> = flow {
                emit(DataMapper.mapResponsesToDomain(data))
            }
        }.asFlow()

    override fun getDetailId(id: String): Flow<UiState<Movie>> =
        object :
            NetworkBoundResource<Movie, MovieResponse>() {
            override fun loadFromDB(): Flow<Movie> = flow { emit(Movie()) }

            override fun shouldFetch(data: Movie?): Boolean =
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                remoteDataSource.getDetailMovie(id)

            override suspend fun saveCallResult(data: MovieResponse) {}

            override fun loadFromAPI(data: MovieResponse): Flow<Movie> = flow {
                emit(DataMapper.movieResponseToDomain(data))
            }
        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteMovieById(id: Int): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovieById(id).map {
            DataMapper.mapFavoriteEntitiesToDomain(it)
        }
    }

    override suspend fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val favoriteEntity = DataMapper.mapDomainToFavoriteEntity(movie)

        localDataSource.setFavoriteMovie(favoriteEntity, state)
    }
}

