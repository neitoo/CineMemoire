package com.neito.cinememoire.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.neito.cinememoire.R
import com.neito.cinememoire.presentation.SearchViewModel
import com.neito.cinememoire.navigation.Screens
import com.neito.cinememoire.presentation.BottomSheetContent
import com.neito.cinememoire.presentation.BottomSheetViewModel
import com.neito.cinememoire.presentation.componentes.MovieItem
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import androidx.compose.runtime.LaunchedEffect as LaunchedEffect


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModelSheet: BottomSheetViewModel) {
    val viewModelSearch = koinViewModel<SearchViewModel>()

    val searchResults by viewModelSearch.searchResults.collectAsState()
    val isLoading by viewModelSearch.isLoading.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchButtonClick: () -> Unit = {
        keyboardController?.hide()
        val trimmedText = viewModelSearch.textState.trim()
        if (trimmedText != viewModelSearch.previousSearchText) {
            viewModelSearch.performSearch(trimmedText, "1")
            viewModelSearch.previousSearchText = trimmedText
        }
    }

    var isTextChanged = false
    val onTextFieldValueChange: (String) -> Unit = { newText ->
        val trimmedText = newText.trimStart()

        if (viewModelSearch.textState != trimmedText) {
            isTextChanged = true
        }

        viewModelSearch.textState = trimmedText

        if (trimmedText.isEmpty() && isTextChanged) {
            isTextChanged = false
            viewModelSearch.clearSearchResults()
            viewModelSearch.previousSearchText = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.TopCenter
    ){
        Column {
            TextField(
                value = viewModelSearch.textState,
                maxLines = 1,
                onValueChange = onTextFieldValueChange,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        onSearchButtonClick()
                    }
                ),
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 5.dp)
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

            if (isLoading) {
                CustomLinearProgressBar()
            }

            val firstBoxModifier = if (viewModelSearch.textState.isEmpty() || searchResults == null) {
                Modifier
                    .fillMaxWidth()
                    .height(0.dp)
            } else {
                Modifier.fillMaxSize()
            }

            val secondBoxModifier = if (viewModelSearch.textState.isEmpty() || searchResults == null) {
                Modifier.fillMaxSize()
            } else {
                Modifier
                    .fillMaxWidth()
                    .height(0.dp)
            }

            AnimatedVisibility(
                visible = !viewModelSearch.textState.isEmpty() && searchResults != null,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                Box(
                    modifier = firstBoxModifier.padding(top = 10.dp),
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
                visible = viewModelSearch.textState.isEmpty() || searchResults == null,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                Box(
                    modifier = secondBoxModifier.padding(top = 10.dp)
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
                            onClick = {
                                viewModelSheet.showBottomSheet = true
                                viewModelSheet.bottomSheetContent = BottomSheetContent.CreateContent
                            }
                        ) {
                            Text(text = stringResource(R.string.create_movie_button_text))
                        }
                    }
                }
            }

        }

    }
}

@Composable
private fun CustomLinearProgressBar(){
    Column(modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}