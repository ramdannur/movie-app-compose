package id.ramdannur.movieappcompose.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.ramdannur.movieappcompose.R
import id.ramdannur.movieappcompose.ui.theme.MovieAppComposeTheme


@Composable
fun MovieItem(
    title: String,
    poster: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(poster)
                .crossfade(true)
                .build(),
//            placeholder = painterResource(R.drawable.img_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.weight(2f)
        )
        Column(
            modifier = Modifier
                .padding(PaddingValues(12.dp))
                .weight(4f)
        ) {
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MovieItemPreview() {
    MovieAppComposeTheme {
        MovieItem(
            "Jaket Hoodie Dicoding",
            "https://m.media-amazon.com/images/M/MV5BMTQ2OTE1Mjk0N15BMl5BanBnXkFtZTcwODE3MDAwNA@@._V1_.jpg",
            stringResource(id = R.string.lorem_ipsum_full)
        )
    }
}