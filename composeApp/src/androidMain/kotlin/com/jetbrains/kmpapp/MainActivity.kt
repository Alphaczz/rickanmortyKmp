package com.jetbrains.kmpapp

import ContextProvider
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jetbrains.kmpapp.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //required to intialize applicationContext->ContentProvider.kt (using in InternetCheck )
            //can be used in other functions which required context on android side
            ContextProvider().create(applicationContext)

            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
fun initLogger() {
    Napier.base(DebugAntilog())
}