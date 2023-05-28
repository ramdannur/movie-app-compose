package id.ramdannur.movieappcompose.ui.common

sealed class UiState<T> {
    data class Loading<T>(val data: T? = null) : UiState<T>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error<T>(val errorMessage: String, val data: T? = null) : UiState<T>()
}