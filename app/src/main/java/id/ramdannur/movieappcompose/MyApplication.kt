package id.ramdannur.movieappcompose

import android.app.Application
import id.ramdannur.movieappcompose.core.di.databaseModule
import id.ramdannur.movieappcompose.core.di.networkModule
import id.ramdannur.movieappcompose.core.di.repositoryModule
import id.ramdannur.movieappcompose.di.useCaseModule
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
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}

