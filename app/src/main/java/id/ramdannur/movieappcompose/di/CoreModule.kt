package id.ramdannur.movieappcompose.di

import androidx.room.Room
import id.ramdannur.movieappcompose.data.MovieRepository
import id.ramdannur.movieappcompose.data.source.local.LocalDataSource
import id.ramdannur.movieappcompose.data.source.local.room.MovieDatabase
import id.ramdannur.movieappcompose.data.source.remote.RemoteDataSource
import id.ramdannur.movieappcompose.data.source.remote.network.ApiService
import id.ramdannur.movieappcompose.utils.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }

    factory { get<MovieDatabase>().favoriteDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, Config.LOCAL_DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}

val loggingInterceptor = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Config.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    single {
        MovieRepository(
            get(),
            get()
        )
    }
}