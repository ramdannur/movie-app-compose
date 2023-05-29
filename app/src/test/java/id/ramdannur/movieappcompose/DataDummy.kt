package id.ramdannur.movieappcompose

import id.ramdannur.movieappcompose.core.domain.model.Movie

object DataDummy {
    fun generateDummyMovie(): List<Movie> {
        val newsList = ArrayList<Movie>()
        for (i in 1..10) {
            val news = Movie().copy(
                i,
                "Movie $i",
            )
            newsList.add(news)
        }
        return newsList
    }
}