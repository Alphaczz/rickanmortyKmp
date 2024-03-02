package com.jetbrains.kmpapp.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.MR
import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.model.RickAndMortyData
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import com.jetbrains.kmpapp.screens.detail.DetailScreen
import com.jetbrains.kmpapp.utils.Response
import dev.icerock.moko.resources.compose.colorResource
import io.github.aakira.napier.Napier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay

data object ListScreen : Screen {


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: ListScreenModel = getScreenModel()
        val dataState by screenModel.data.collectAsState(Response.Loading)
        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(3000)
                refreshing = false
            }
        }
        LaunchedEffect(dataState) {
            // This block will be executed whenever dataState changes
            when (dataState) {
                is Response.Loading -> {
                    // Handle loading state
                    Napier.i("Data state changed to Loading")
                }
                is Response.Success -> {
                    // Handle success state
                    Napier.i("Data state changed to Success")
                }
                is Response.Error -> {
                    // Handle error state
                    Napier.i("Data state changed to Error")
                }
            }
        }

      //  val objects by screenModel.objects.collectAsState()
        Box(modifier = Modifier.fillMaxSize()) {
            when (val response = dataState) {
                is Response.Loading -> {
                    Napier.i("isLoading")

                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = colorResource(MR.colors.secondary) // Change the color to your desired color
                    )                }
                is Response.Success -> {
                    val objects = response.data

                    Napier.i("iSSuccessInListScreen ${objects}")
                    AnimatedContent(objects) { objectsAvailable ->
                        if (objectsAvailable!!.isNotEmpty()) {
                            ObjectGrid(
                                objects =objects,
                                onObjectClick = { objectId ->
                                    navigator.push(DetailScreen(objectId.toLong()))
                                }
                            )
                        } else {
                            EmptyScreenContent(Modifier.fillMaxSize())
                        }
                    }
                }
                is Response.Error -> {
                    // Handle error state
                    // For example, display an error message
                }

            }
        }

    }
}


@Composable
private fun ObjectGrid(
    objects: List<Result?>,
    onObjectClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow() {  }
    LazyVerticalGrid(
          columns = GridCells.Adaptive(130.dp),
//        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(objects, key = { it?.id!! }) { obj ->
            ObjectFrame(
                obj = obj!!,
                onClick = { onObjectClick(obj.id) },
            )
        }
    }
}

@Composable
private fun ObjectFrame(
    obj: Result,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        KamelImage(
            resource = asyncPainterResource(data = obj.image),
            contentDescription = obj.status,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray),
        )

        Spacer(Modifier.height(2.dp))

        Text(obj.name, style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
        Text(obj.gender, style = MaterialTheme.typography.body2)
        Text(obj.origin.name, style = MaterialTheme.typography.caption)
    }
}
