package com.c5inco.modifiers.ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.c5inco.modifiers.ui.theme.appDarkColors
import com.c5inco.modifiers.ui.theme.appLightColors

@Composable
fun PlaygroundTheme(
    colors: Colors = MaterialTheme.colors,
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val appColors = if (isDarkTheme) appDarkColors else appLightColors

    DesktopMaterialTheme(
        colors = appColors.copy(
            background = colors.background,
            onBackground = colors.onBackground,
            surface = colors.surface,
            onSurface = colors.onSurface,
        )
    ) {
        content()
    }
}