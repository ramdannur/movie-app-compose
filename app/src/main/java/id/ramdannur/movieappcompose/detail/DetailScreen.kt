package id.ramdannur.movieappcompose.detail


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.ramdannur.movieappcompose.R
import id.ramdannur.movieappcompose.core.ui.common.UiState
import id.ramdannur.movieappcompose.core.ui.components.RatingBar
import id.ramdannur.movieappcompose.theme.MovieAppComposeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    movieId: Long,
    viewModel: DetailViewModel = koinViewModel(),
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val isFavorite by viewModel.isFavorite.collectAsState()
    val message by viewModel.message.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.checkFavoriteStatus(movieId)
    }

    if (message != null) {
        LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading()).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getRewardById(movieId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.title ?: "",
                    data.backdropPathWithUrl,
                    data.overview ?: "",
                    data.voteAverage,
                    isFavorite,
                    onBackClick = navigateBack,
                    onFavoriteClick = {
                        isFavorite?.let {
                            viewModel.setFavoriteMovie(data, !it)
                        }
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    title: String,
    backdrop: String,
    description: String,
    voteAverage: Double?,
    isFavorite: Boolean?,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(backdrop)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.img_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(220.dp)
                        .fillMaxWidth()
                        // Workaround to enable alpha compositing
                        .graphicsLayer { alpha = 0.99f }
                        .drawWithContent {
                            val colors = listOf(
                                Color.Black,
                                Color.Transparent
                            )
                            drawContent()
                            drawRect(
                                brush = Brush.verticalGradient(colors),
                                blendMode = BlendMode.DstIn
                            )
                        }

                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = Color.White,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onBackClick() }
                    )
                    isFavorite?.let {
                        val favoriteIconTint = if (it) Color.Red else Color.White

                        Icon(
                            imageVector = Icons.Default.Favorite,
                            tint = favoriteIconTint,
                            contentDescription = stringResource(R.string.back),
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable { onFavoriteClick() }
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    voteAverage?.let {
                        RatingBar(rating = it, starsColor = LightGray)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "%.1f".format(it))
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    MovieAppComposeTheme {
        DetailContent(
            "Jaket Hoodie Dicoding",
            "https://m.media-amazon.com/images/M/MV5BMTQ2OTE1Mjk0N15BMl5BanBnXkFtZTcwODE3MDAwNA@@._V1_.jpg",
            stringResource(id = R.string.lorem_ipsum_full),
            3.5,
            false,
            onBackClick = {},
            onFavoriteClick = {},
        )
    }
}