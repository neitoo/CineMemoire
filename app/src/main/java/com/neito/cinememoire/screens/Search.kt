package com.neito.cinememoire.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neito.cinememoire.R
import com.neito.cinememoire.presentation.SearchViewModel
import com.neito.cinememoire.navigation.Screens
import com.neito.cinememoire.presentation.componentes.MovieItem
import org.koin.androidx.compose.get


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    val viewModel: SearchViewModel = get()

    var textState by remember {
        mutableStateOf("")
    }

    val searchResults by viewModel.searchResults.collectAsState()

    val onSearchButtonClick: () -> Unit = {
        viewModel.performSearch(textState, "1")
    }

    val onTextFieldValueChange: (String) -> Unit = { newText ->
        // Обнуление searchResults, если новый текст пуст
        if (newText.isEmpty()) {
            viewModel.clearSearchResults()
        }
        textState = newText
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
                onValueChange = onTextFieldValueChange,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.search_textfield_label))
                },
                trailingIcon = {
                    IconButton(onClick = onSearchButtonClick) {
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

            val firstBoxModifier = if (textState.isEmpty() || searchResults == null) {
                Modifier.fillMaxWidth().height(0.dp)
            } else {
                Modifier.fillMaxSize()
            }

            val secondBoxModifier = if (textState.isEmpty() || searchResults == null) {
                Modifier.fillMaxSize()
            } else {
                Modifier.fillMaxWidth().height(0.dp)
            }

            AnimatedVisibility(
                visible = !textState.isEmpty() && searchResults != null,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                Box(
                    modifier = firstBoxModifier,
                    contentAlignment = Alignment.TopCenter
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        searchResults?.films?.forEach { film ->
                            item {
                                MovieItem(movie = film)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }

                }
            }


            AnimatedVisibility(
                visible = textState.isEmpty() || searchResults == null,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                Box(
                    modifier = secondBoxModifier
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
                            onClick = { navController.navigate(Screens.CreateScreen.screen) }
                        ) {
                            Text(text = stringResource(R.string.create_movie_button_text))
                        }
                    }
                }
            }

        }

    }
}