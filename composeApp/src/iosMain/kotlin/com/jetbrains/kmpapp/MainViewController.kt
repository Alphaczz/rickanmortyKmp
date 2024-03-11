package com.jetbrains.kmpapp

import androidx.compose.ui.window.ComposeUIViewController
import com.jetbrains.kmpapp.data.factory.ApplicationComponent

fun MainViewController() = ComposeUIViewController { App() }
fun initialize() {
    ApplicationComponent.init()
}