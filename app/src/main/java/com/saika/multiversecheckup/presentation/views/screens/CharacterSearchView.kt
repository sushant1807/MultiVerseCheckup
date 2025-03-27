package com.saika.multiversecheckup.presentation.views.screens

import android.content.res.Configuration
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.saika.multiversecheckup.R
import com.saika.multiversecheckup.domain.model.Character
import com.saika.multiversecheckup.domain.util.Resource
import com.saika.multiversecheckup.presentation.viewmodel.CharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSearchView(
    viewModel: CharacterViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                modifier = Modifier.shadow(10.dp)
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    viewModel.searchImages(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.lbl_search_character)) }
            )

            when (uiState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is Resource.Success -> {
                    val characters = (uiState as Resource.Success).data
                    LazyVerticalGrid(columns = GridCells.Fixed(if (isLandscape) 4 else 3)) {
                        items(characters) { character ->
                            ImageItem(
                                character = character,
                                onClick = { onItemClick(character.id) })
                        }
                    }
                }

                is Resource.Error -> {
                    Text(
                        text = (uiState as Resource.Error).message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun ImageItem(character: Character, onClick: () -> Unit) {
    val transitionState = remember { MutableTransitionState(false) }
    val scale by animateFloatAsState(
        targetValue = if (transitionState.targetState) 1.2f else 1f,
        animationSpec = tween(durationMillis = 300), label = ""
    )
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                transitionState.targetState = true
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center // Centers image
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale),
                contentScale = ContentScale.Crop,

                )
        }
    }
}
