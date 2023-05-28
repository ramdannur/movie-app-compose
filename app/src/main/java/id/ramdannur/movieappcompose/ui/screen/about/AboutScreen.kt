package id.ramdannur.movieappcompose.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.ramdannur.movieappcompose.R
import id.ramdannur.movieappcompose.ui.theme.MovieAppComposeTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_profile),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .border(2.dp, Color.LightGray, CircleShape)   // add a border (optional)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Ramdan Nurul",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Spacer(modifier = Modifier.height(18.dp))

        ProfileSectionItem(
            title = "Nama Panggilan",
            value = "Ramdan"
        )

        ProfileSectionItem(
            title = "Email",
            value = "Ramdannur12@gmail.com"
        )

        ProfileSectionItem(
            title = "Pekerjaan",
            value = "Software Engineer"
        )

        ProfileSectionItem(
            title = "Hobi",
            value = "Travelling"
        )

    }
}

@Composable
fun ProfileSectionItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier.padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = value,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Preview(showBackground = true, device = Devices.NEXUS_5)
@Composable
fun AboutScreenPreview() {
    MovieAppComposeTheme {
        AboutScreen()
    }
}