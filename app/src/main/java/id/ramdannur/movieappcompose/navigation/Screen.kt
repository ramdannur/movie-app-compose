package id.ramdannur.movieappcompose.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Detail : Screen("home/{movieId}") {
        fun createRoute(movieId: Long) = "home/$movieId"
    }
}