package com.c5inco.modifiers.ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable

@Composable
fun PlaygroundTheme(
    colors: Colors,
    content: @Composable () -> Unit
) {
    DesktopMaterialTheme(
        colors = colors
    ) {
        content()
    }
}