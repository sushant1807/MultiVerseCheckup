package com.saika.multiversecheckup.presentation.views.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.saika.multiversecheckup.R
import com.saika.multiversecheckup.domain.util.Resource
import com.saika.multiversecheckup.presentation.viewmodel.CharacterDetailViewModel
import com.saika.multiversecheckup.presentation.views.components.LabeledDetail
import com.saika.multiversecheckup.utils.formatDate
import com.saika.multiversecheckup.utils.shareImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailView(
    id: Int,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
    navController: NavController
) {

    Log.e("App ID 2", id.toString())
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(id) { viewModel.loadCharacter(id) }

    when (val state = viewModel.uiState) {
        is Resource.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is Resource.Error -> Text("Error: ${state.message}")
        is Resource.Success -> {
            val character = state.data
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(text = "") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(
                                        R.string.lbl_back
                                    )
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                shareImage(
                                    context,
                                    character = character
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = stringResource(
                                        R.string.lbl_share
                                    )
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                if (isLandscape) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = character.image,
                            contentDescription = character.name,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                                .padding(vertical = 16.dp),

                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = character.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            LabeledDetail(
                                text = character.species,
                                contentDescription = character.species
                            )


                            Spacer(modifier = Modifier.height(6.dp))

                            LabeledDetail(
                                text = character.status,
                                contentDescription = character.status
                            )


                            Spacer(modifier = Modifier.height(6.dp))

                            LabeledDetail(
                                text = character.origin,
                                contentDescription = character.origin
                            )

                            character.type?.takeIf { it.isNotBlank() }?.let {

                                Spacer(modifier = Modifier.height(6.dp))

                                LabeledDetail(text = it, contentDescription = it)

                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            LabeledDetail(
                                text = formatDate(character.created),
                                contentDescription = formatDate(character.created)
                            )

                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {

                            AsyncImage(
                                model = character.image,
                                contentDescription = character.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        item {

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        item {

                            Text(
                                text = character.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }

                        item {

                            Spacer(modifier = Modifier.height(6.dp))
                        }

                        item {

                            LabeledDetail(
                                text = character.species,
                                contentDescription = character.species
                            )
                        }

                        item {

                            Spacer(modifier = Modifier.height(6.dp))
                        }

                        item {

                            LabeledDetail(
                                text = character.status,
                                contentDescription = character.status
                            )
                        }

                        item {

                            Spacer(modifier = Modifier.height(6.dp))
                        }

                        item {

                            LabeledDetail(
                                text = character.origin,
                                contentDescription = character.origin
                            )

                        }

                        item {
                            Spacer(modifier = Modifier.height(6.dp))
                        }

                        item {

                            LabeledDetail(
                                text = formatDate(character.created),
                                contentDescription = formatDate(character.created)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(6.dp))
                        }

                        item {
                            character.type?.takeIf { it.isNotBlank() }?.let {
                                LabeledDetail(text = it, contentDescription = it)
                            }
                        }
                    }
                }
            }
        }
    }
}
