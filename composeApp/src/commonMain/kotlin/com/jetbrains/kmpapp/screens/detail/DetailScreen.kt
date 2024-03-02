package com.jetbrains.kmpapp.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.waterfallPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.MR
import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.model.RickAndMortyData
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data class DetailScreen(val objectId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: DetailScreenModel = getScreenModel()

        val obj by screenModel.getObject(objectId).collectAsState(initial = null)
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedContent(obj != null) { objectAvailable ->
                if (objectAvailable) {
                    ObjectDetails(obj!!, onBackClick = { navigator.pop() })
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = colorResource(MR.colors.secondary) // Change the color to your desired color
                    )
                }
            }
        }
    }
}

@Composable
private fun ObjectDetails(
    obj: Result,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = colorResource(MR.colors.primary) ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, stringResource(MR.strings.back), tint = colorResource(MR.colors.secondary))
                }
            }
        },
        modifier = modifier,
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues).background(colorResource(MR.colors.primary))
        ) {
            KamelImage(
                resource = asyncPainterResource(data = obj.image),
                contentDescription = obj.status,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth().padding(8.dp)
                    .background(Color.LightGray).waterfallPadding()
            )


            SelectionContainer {
                Column(Modifier.padding(12.dp)) {
                    Text(obj.name,  style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold, color = colorResource(MR.colors.white)))
                    Spacer(Modifier.height(6.dp))
                    LabeledInfo(stringResource(MR.strings.label_title), obj.name)
                    LabeledInfo(stringResource(MR.strings.label_artist), obj.species)
                    LabeledInfo(stringResource(MR.strings.label_date), obj.location.name)
                    LabeledInfo(stringResource(MR.strings.label_dimensions), obj.gender)
                    LabeledInfo(stringResource(MR.strings.label_medium), obj.status)
                    LabeledInfo(stringResource(MR.strings.label_department), obj.type)
                    LabeledInfo(stringResource(MR.strings.label_repository), obj.url)
                    LabeledInfo(stringResource(MR.strings.label_credits), obj.created)
                }
            }
        }
    }
}

@Composable
private fun LabeledInfo(
    label: String,
    data: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(vertical = 4.dp)) {
        Spacer(Modifier.height(6.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(MR.colors.white))) {
                    append("$label: $data")
                }

            }
        )
    }
}
