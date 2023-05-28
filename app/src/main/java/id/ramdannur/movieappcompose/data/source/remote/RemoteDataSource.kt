package id.ramdannur.movieappcompose.data.source.remote

import android.util.Log
import id.ramdannur.movieappcompose.data.source.remote.network.ApiResponse
import id.ramdannur.movieappcompose.data.source.remote.network.ApiService
import id.ramdannur.movieappcompose.data.source.remote.response.MovieResponse
import id.ramdannur.movieappcompose.utils.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getDiscoverMovie(): Flow<ApiResponse<List<MovieResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getList(apiKey = Config.API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearchMovie(keyword: String): Flow<ApiResponse<List<MovieResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getSearch(apiKey = Config.API_KEY, query = keyword)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(id: String): Flow<ApiResponse<MovieResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getDetail(apiKey = Config.API_KEY, id = id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

