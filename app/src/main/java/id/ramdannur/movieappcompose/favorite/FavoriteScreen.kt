package id.ramdannur.movieappcompose.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.ramdannur.movieappcompose.core.domain.model.Movie
import id.ramdannur.movieappcompose.core.ui.common.UiState
import id.ramdannur.movieappcompose.core.ui.components.EmptyPage
import id.ramdannur.movieappcompose.core.ui.components.MovieItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = koinViewModel(),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading()).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteMovies()
            }

            is UiState.Success -> {
                FavoriteContent(
                    listMovie = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }

            is UiState.Error -> {
                EmptyPage(uiState.errorMessage)
            }
        }
    }
}

@Composable
fun FavoriteContent(
    listMovie: List<Movie>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    if (listMovie.isNotEmpty()) {

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(listMovie) { data ->
                MovieItem(
                    poster = data.posterPathWithUrl,
                    title = data.title ?: "",
                    description = data.overview ?: "",
                    modifier = Modifier.clickable {
                        navigateToDetail(data.id.toLong())
                    }
                )
            }
        }
    } else {
        EmptyPage()
    }
}