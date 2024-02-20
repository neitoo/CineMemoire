package com.neito.cinememoire.presentation.componentes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.neito.cinememoire.data.remote.dto.Film

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieItem(movie: Film){
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(movie.posterUrlPreview)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val scrollName = rememberScrollState(0)
    val scrollGenres = rememberScrollState(0)

    val genres = movie.genres.joinToString(", ") { it.genre }

    val paddingModifier  = Modifier.padding(bottom = 15.dp)

    Card(
        modifier = paddingModifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if(imageState is AsyncImagePainter.State.Loading){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(22.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CustomCircularProgressBar()
                }
            }
            if (imageState is AsyncImagePainter.State.Success){
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    painter = imageState.painter,
                    contentDescription = movie.nameEn,
                    contentScale = ContentScale.Crop)
            }

            Text(
                text = movie.nameRu ?: movie.nameEn ?: "",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 4.dp)
                    .horizontalScroll(scrollName)
            )
            Text(
                text = movie.year,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .alpha(0.7f)
            )
            Text(
                text = genres,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .alpha(0.7f)
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                        delayMillis = 0,
                        initialDelayMillis = 0,
                        velocity = 40.dp
                    )
            )
        }
    }
}

@Composable
private fun CustomCircularProgressBar(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
        )
    }
}