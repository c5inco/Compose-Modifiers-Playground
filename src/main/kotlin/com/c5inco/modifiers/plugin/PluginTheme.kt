package com.c5inco.modifiers.plugin

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.c5inco.modifiers.plugin.intellij.SwingColor
import com.c5inco.modifiers.ui.PlaygroundTheme
import com.intellij.util.ui.UIUtil

@Composable
fun PluginTheme(
    content: @Composable () -> Unit
) {
    val isDarcula = UIUtil.isUnderDarcula()
    val colors = if (isDarcula) darkColors() else lightColors()
    val swingColor = SwingColor()

    PlaygroundTheme(
        colors = colors.copy(
            background = swingColor.background,
            onBackground = swingColor.onBackground,
            surface = swingColor.surface,
            onSurface = swingColor.onSurface,
        ),
        isDarkTheme = isDarcula,
        content = content
    )
}