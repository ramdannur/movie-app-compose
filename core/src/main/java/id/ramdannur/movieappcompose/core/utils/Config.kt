package id.ramdannur.movieappcompose.core.utils

import id.ramdannur.movieappcompose.core.BuildConfig

object Config {
    const val LOCAL_DB_NAME: String = BuildConfig.LOCAL_DB_NAME
    const val SQLITE_PASSPHRASE: String = BuildConfig.SQLITE_PASSPHRASE

    const val API_HOST: String = BuildConfig.API_HOST
    const val API_BASE_URL: String = "https://$API_HOST/3/"
    const val API_KEY: String = BuildConfig.API_KEY

    private const val THUMB_IMAGE_BASE_URL: String = "https://${BuildConfig.THUMB_IMAGE_HOST}/t/p"
    const val THUMB_IMAGE_MEDIUM: String = "$THUMB_IMAGE_BASE_URL/w342"
    const val THUMB_IMAGE_BIG: String = "$THUMB_IMAGE_BASE_URL/w780"
}