package com.jetbrains.kmpapp.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfallPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
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
import dev.icerock.moko.media.Bitmap
import dev.icerock.moko.media.compose.BindMediaPickerEffect
import dev.icerock.moko.media.compose.rememberMediaPickerControllerFactory
import dev.icerock.moko.media.compose.toImageBitmap
import dev.icerock.moko.media.picker.MediaSource
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory

import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch

data class DetailScreen(val objectId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: DetailScreenModel = getScreenModel()
        val coroutineScope = rememberCoroutineScope()

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
//sample push

@Composable
private fun ObjectDetails(
    obj: Result,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val mediaFactory = rememberMediaPickerControllerFactory()
    val picker = remember(mediaFactory) { mediaFactory.createMediaPickerController() }
    BindMediaPickerEffect(picker)

    var pfpBitmap: Bitmap? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = colorResource(MR.colors.primary) ) {
                IconButton(onClick = /*onBackClick*/{  }) {
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


            TextButton(
                modifier = Modifier
                    .width(150.dp)
                    .height(45.dp).padding(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                shape = CircleShape,
                onClick = {
                    scope.launch {
                        picker.permissionsController.providePermission(Permission.GALLERY)
                        if (picker.permissionsController.isPermissionGranted(Permission.GALLERY)) {
                            pfpBitmap = picker.pickImage(MediaSource.GALLERY)
                        }
                    }
                }
            ) {
                Text(
                    text = "Choose photo",
                    fontWeight = FontWeight.Bold,
                )
            }
            TextButton(
                modifier = Modifier
                    .width(150.dp)
                    .height(45.dp).padding(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                shape = CircleShape,
                onClick = {
                    scope.launch {
                        picker.permissionsController.providePermission(Permission.CAMERA)
                        if (picker.permissionsController.isPermissionGranted(Permission.CAMERA)) {
                            pfpBitmap = picker.pickImage(MediaSource.CAMERA)                        }
                    }
                }
            ) {
                Text(
                    text = "Click Photo",
                    fontWeight = FontWeight.Bold,
                )
            }


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
@Composable
private fun PhotoContainer(
    modifier: Modifier = Modifier,
    photoUrl: ImageBitmap?,
    onOpenPhoto: () -> Unit,
) {
    IconButton(
        modifier = modifier
            .size(
                height = 200.dp,
                width = 200.dp
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    10.dp
                )
            )
            .border(
               5.dp,
                colorResource(MR.colors.primary) ,
                shape = RoundedCornerShape(10.dp)
            ),
        onClick = onOpenPhoto
    ) {
        if (photoUrl != null) {
            Image(
                bitmap = photoUrl,
                contentDescription = "imagem selecionada",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        } else {
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    tint =  colorResource(MR.colors.primary),
                    contentDescription = "Camera",
                    modifier = Modifier.size(10.dp)
                )

                Text(
                    text = "Tirar foto",
                    style = MaterialTheme.typography.h3,

                )
            }
        }
    }
}