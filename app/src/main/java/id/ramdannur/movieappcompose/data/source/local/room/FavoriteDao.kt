package id.ramdannur.movieappcompose.data.source.local.room

import androidx.room.*
import id.ramdannur.movieappcompose.data.source.local.entity.FavoriteEntity
import id.ramdannur.movieappcompose.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun findFavoriteWithId(id: Int): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: List<FavoriteEntity>)

    @Delete
    fun delete(favorite: FavoriteEntity)
}
