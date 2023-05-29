package id.ramdannur.movieappcompose.core.utils

object Config {
    const val LOCAL_DB_NAME = "Movie.db"

    const val API_BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "6a631cfb34027be57967a27d800e3061"

    private const val THUMB_IMAGE_HOST = "https://image.tmdb.org/t/p"
    const val THUMB_IMAGE_MEDIUM = "$THUMB_IMAGE_HOST/w342"
    const val THUMB_IMAGE_BIG = "$THUMB_IMAGE_HOST/w780"
}