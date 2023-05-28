package id.ramdannur.movieappcompose.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String?,

    @ColumnInfo(name = "poster_path")
    var posterPath: String?,

    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double
)
