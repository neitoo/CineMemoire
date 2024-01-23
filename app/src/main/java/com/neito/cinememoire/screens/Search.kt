package com.neito.cinememoire.screens

import android.graphics.drawable.Icon
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.neito.cinememoire.R
import com.neito.cinememoire.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    var textState by remember {
        mutableStateOf("")
    }

    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.TopCenter
    ){
        Column {
            TextField(
                value = textState,
                onValueChange = {textState = it},
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.search_textfield_label))
                },
                trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.a_search_icon),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(20.dp)
            )

            val blockHeight by animateDpAsState(
                if (textState.isEmpty()) 170.dp else 0.dp,
                animationSpec = tween(durationMillis = 300), label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(blockHeight)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        text = stringResource(R.string.search_info_text),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        text = stringResource(R.string.and_text),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    Button(
                        modifier = Modifier.padding(top = 20.dp),
                        onClick = { }
                    ) {
                        Text(text = stringResource(R.string.create_movie_button_text))
                    }
                }
            }

        }

    }
}