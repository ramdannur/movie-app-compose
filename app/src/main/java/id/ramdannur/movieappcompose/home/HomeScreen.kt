package id.ramdannur.movieappcompose.home


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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import id.ramdannur.movieappcompose.R
import id.ramdannur.movieappcompose.core.domain.model.Movie
import id.ramdannur.movieappcompose.core.ui.common.UiState
import id.ramdannur.movieappcompose.core.ui.components.EmptyPage
import id.ramdannur.movieappcompose.core.ui.components.MovieItem
import id.ramdannur.movieappcompose.core.ui.components.SearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    navigateToDetail: (Long) -> Unit,
) {
    val searchInput = viewModel.keyword

    viewModel.uiState.collectAsState(initial = UiState.Loading()).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                if (searchInput.isEmpty()) {
                    viewModel.getAllMovies()
                } else {
                    viewModel.searchMovie(searchInput)
                }
            }

            is UiState.Success -> {
                HomeContent(
                    searchInput = searchInput,
                    onSearchValueChange = { newInput ->
                        viewModel.setKeyword(newInput)
                    },
                    listMovie = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                ) {
                    viewModel.searchMovie(it)
                }
            }

            is UiState.Error -> {
                EmptyPage(uiState.errorMessage)
            }
        }
    }
}

@Composable
fun HomeContent(
    searchInput: String,
    onSearchValueChange: (String) -> Unit,
    listMovie: List<Movie>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onSearchMovie: (String) -> Unit,
) {
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
                    text = searchInput,
                    onValueChange = onSearchValueChange,
                    onEnter = {
                        focusManager.clearFocus()
                        onSearchMovie(searchInput)
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