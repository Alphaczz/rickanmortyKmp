package com.jetbrains.kmpapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.kmpapp.screens.list.ListScreen
import dev.icerock.moko.resources.compose.colorResource

@Composable
fun App() {
    val backgroundColor = colorResource(MR.colors.primary)

    MaterialTheme(
        colors = lightColors(
            primary = colorResource(MR.colors.primary),
            secondary = colorResource(MR.colors.secondary),
            // Add more colors as needed
        )
    ) {
        Surface(
            color = backgroundColor, // Set the background color here
            modifier = Modifier.fillMaxSize()
        ) {
//            val coroutineScope = rememberCoroutineScope()
//            var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
//            var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
//            var launchCamera by remember { mutableStateOf(value = false) }
//            var launchGallery by remember { mutableStateOf(value = false) }
//            var launchSetting by remember { mutableStateOf(value = false) }
//            var permissionRationalDialog by remember { mutableStateOf(value = false) }
//            val permissionsManager = createPermissionsManager(object : PermissionCallback {
//                override fun onPermissionStatus(
//                    permissionType: PermissionType,
//                    status: PermissionStatus
//                ) {
//                    when (status) {
//                        PermissionStatus.GRANTED -> {
//                            when (permissionType) {
//                                PermissionType.CAMERA -> launchCamera = true
//                                PermissionType.GALLERY -> launchGallery = true
//                            }
//                        }
//
//                        else -> {
//                            permissionRationalDialog = true
//                        }
//                    }
//                }
//
//
//            })
//
//            val cameraManager = rememberCameraManager {
//                coroutineScope.launch {
//                    val bitmap = withContext(Dispatchers.Default) {
//                        it?.toImageBitmap()
//                    }
//                    imageBitmap = bitmap
//                }
//            }
//
//            val galleryManager = rememberGalleryManager {
//                coroutineScope.launch {
//                    val bitmap = withContext(Dispatchers.Default) {
//                        it?.toImageBitmap()
//                    }
//                    imageBitmap = bitmap
//                }
//            }
//            if (imageSourceOptionDialog) {
//                ImageSourceOptionDialog(onDismissRequest = {
//                    imageSourceOptionDialog = false
//                }, onGalleryRequest = {
//                    imageSourceOptionDialog = false
//                    launchGallery = true
//                }, onCameraRequest = {
//                    imageSourceOptionDialog = false
//                    launchCamera = true
//                })
//            }
//            if (launchGallery) {
//                if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
//                    galleryManager.launch()
//                } else {
//                    permissionsManager.askPermission(PermissionType.GALLERY)
//                }
//                launchGallery = false
//            }
//            if (launchCamera) {
//                if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
//                    cameraManager.launch()
//                } else {
//                    permissionsManager.askPermission(PermissionType.CAMERA)
//                }
//                launchCamera = false
//            }
//            if (launchSetting) {
//                permissionsManager.launchSettings()
//                launchSetting = false
//            }
//            if (permissionRationalDialog) {
//                AlertMessageDialog(title = "Permission Required",
//                    message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
//                    positiveButtonText = "Settings",
//                    negativeButtonText = "Cancel",
//                    onPositiveClick = {
//                        permissionRationalDialog = false
//                        launchSetting = true
//
//                    },
//                    onNegativeClick = {
//                        permissionRationalDialog = false
//                    })
//
//            }
//            Box(
//                modifier = Modifier.fillMaxSize().background(Color.DarkGray),
//                contentAlignment = Alignment.Center
//            ) {
//                if (imageBitmap != null) {
//                    Image(
//                        bitmap = imageBitmap!!,
//                        contentDescription = "Profile",
//                        modifier = Modifier.size(100.dp).clip(CircleShape).clickable {
//                            imageSourceOptionDialog = true
//                        },
//                        contentScale = ContentScale.Crop
//                    )
//                } else {
////                    Image(
////                        modifier = Modifier.size(100.dp).clip(CircleShape).clickable {
////                            imageSourceOptionDialog = true
////                        },
////                        painter = painterResource("ic_person_circle.xml"),
////                        contentDescription = "Profile",
////                    )
//                }
//            }
//        }
            Navigator(ListScreen)
        }
    }

    }

