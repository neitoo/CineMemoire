package com.neito.cinememoire.presentation.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neito.cinememoire.data.remote.dto.Film
import com.neito.cinememoire.data.remote.dto.SearchResponse

@Composable
fun MovieItem(movie: Film){
    val paddingModifier  = Modifier.padding(bottom = 15.dp)

    Card(
        modifier = paddingModifier.size(150.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = movie.nameRu ?: movie.nameEn,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = movie.year,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}