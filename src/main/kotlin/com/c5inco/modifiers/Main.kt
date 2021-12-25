package com.c5inco.modifiers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.c5inco.modifiers.ui.PlaygroundTheme
import com.c5inco.modifiers.ui.theme.appDarkColors
import com.c5inco.modifiers.ui.theme.appLightColors

val DarkActive = compositionLocalOf { false }

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Modifiers Playground",
        state = rememberWindowState(width = 1100.dp, height = 800.dp),
        icon = painterResource("/META-INF/macosicon.png")
    ) {
        var darkTheme by remember { mutableStateOf(false) }

        PlaygroundTheme(colors = if (darkTheme) appDarkColors else appLightColors) {
            Box(Modifier.fillMaxSize()) {
                CompositionLocalProvider(DarkActive provides darkTheme) {
                    Application()
                }
                Column(
                    Modifier
                        .size(24.dp)
                        .clickable {
                            darkTheme = !darkTheme
                        }
                ) { }
            }
        }
    }
}