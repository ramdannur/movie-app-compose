package id.ramdannur.movieappcompose

import android.app.Application
import id.ramdannur.movieappcompose.di.databaseModule
import id.ramdannur.movieappcompose.di.networkModule
import id.ramdannur.movieappcompose.di.repositoryModule
import id.ramdannur.movieappcompose.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}

