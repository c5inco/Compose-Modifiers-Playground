package com.c5inco.modifiers.plugin

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.c5inco.modifiers.plugin.intellij.SwingColor
import com.c5inco.modifiers.ui.PlaygroundTheme

@Composable
fun PluginTheme(
    content: @Composable () -> Unit
) {
    val swingColor = SwingColor()

    PlaygroundTheme(
        colors = MaterialTheme.colors.copy(
            primary = swingColor.primary,
            secondary = swingColor.secondary,
            background = swingColor.background,
            onBackground = swingColor.onBackground,
            surface = swingColor.surface,
            onSurface = swingColor.onSurface,
        ),
        content = content
    )
}