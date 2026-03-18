package com.antoniocostadossantos.listadecompras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.antoniocostadossantos.listadecompras.core.ui.theme.DefaultDarkBackground
import com.antoniocostadossantos.listadecompras.core.ui.theme.ListaDeComprasTheme
import com.antoniocostadossantos.listadecompras.presenter.features.home.ui.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(DefaultDarkBackground.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(DefaultDarkBackground.toArgb()),
        )
        setContent {
            ListaDeComprasTheme {
                HomeScreen()
            }
        }
    }
}
