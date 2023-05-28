package id.ramdannur.movieappcompose.ui.screen.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import id.ramdannur.movieappcompose.R
import id.ramdannur.movieappcompose.model.Movie
import id.ramdannur.movieappcompose.ui.common.UiState
import id.ramdannur.movieappcompose.ui.components.EmptyPage
import id.ramdannur.movieappcompose.ui.components.MovieItem
import id.ramdannur.movieappcompose.ui.components.SearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading()).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllMovies()
            }

            is UiState.Success -> {
                HomeContent(
                    listMovie = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    onSearchMovie = {
                        viewModel.searchMovie(it)
                    },
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    listMovie: List<Movie>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onSearchMovie: (String) -> Unit,
) {
    var input by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    LazyColumn(
        contentPadding = PaddingValues(16.dp, 0.dp, 16.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        item {
            Box(modifier = modifier) {
                Image(
                    painter = painterResource(R.drawable.img_abstract),
                    contentDescription = "Banner Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                SearchBar(
                    text = input,
                    onValueChange = { newInput ->
                        input = newInput
                    },
                    onEnter = {
                        focusManager.clearFocus()
                        onSearchMovie(input.trim())
                    }
                )
            }
        }

        if (listMovie.isNotEmpty()) {
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
        } else {
            item {
                Column {
                    Spacer(modifier = Modifier.height(70.dp))
                    EmptyPage()
                }
            }
        }
    }
}