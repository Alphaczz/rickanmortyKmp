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
            primary = colorResource(MR.colors.primary) ,
            secondary =colorResource(MR.colors.secondary),
            // Add more colors as needed
        )
    ){
        Surface(
            color = backgroundColor, // Set the background color here
            modifier = Modifier.fillMaxSize()
        ){
            Navigator(ListScreen)
        }

    }
}
