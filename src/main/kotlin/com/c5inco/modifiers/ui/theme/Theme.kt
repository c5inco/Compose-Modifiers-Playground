package com.c5inco.modifiers.ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.runtime.Composable
import com.c5inco.modifiers.ui.theme.appLightColors

@Composable
fun PlaygroundTheme(
    content: @Composable () -> Unit
) {
    DesktopMaterialTheme(
        colors = appLightColors
    ) {
        content()
    }
}