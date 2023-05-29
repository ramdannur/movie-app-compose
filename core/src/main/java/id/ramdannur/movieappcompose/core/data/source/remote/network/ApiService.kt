package id.ramdannur.movieappcompose.core.data.source.remote.network

import id.ramdannur.movieappcompose.core.data.source.remote.response.ListMovieResponse
import id.ramdannur.movieappcompose.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getList(@Query("api_key") apiKey: String): ListMovieResponse

    @GET("search/movie")
    suspend fun getSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): ListMovieResponse

    @GET("movie/{id}")
    suspend fun getDetail(@Path("id") id: String, @Query("api_key") apiKey: String): MovieResponse
}
