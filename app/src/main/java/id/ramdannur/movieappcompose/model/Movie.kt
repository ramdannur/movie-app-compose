package id.ramdannur.movieappcompose.model

import id.ramdannur.movieappcompose.utils.Config

data class Movie(
    val id: Int,
    val title: String?,
    val overview: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?
) {
    constructor() : this(id = -1, null, null, null, null, null, null)

    val posterPathWithUrl: String
        get() = Config.THUMB_IMAGE_MEDIUM + posterPath

    val backdropPathWithUrl: String
        get() = Config.THUMB_IMAGE_BIG + backdropPath
}