package id.ramdannur.movieappcompose.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ramdannur.movieappcompose.core.data.source.local.entity.FavoriteEntity
import id.ramdannur.movieappcompose.core.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun favoriteDao(): FavoriteDao
}