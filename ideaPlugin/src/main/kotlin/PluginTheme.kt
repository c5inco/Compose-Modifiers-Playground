package com.c5inco.modifiers.plugin

import androidx.compose.runtime.Composable
import com.c5inco.modifiers.plugin.intellij.SwingColors
import com.intellij.ui.JBColor
import ui.theme.PlaygroundTheme
import ui.theme.appDarkColors
import ui.theme.appLightColors

@Composable
fun PluginTheme(
    content: @Composable () -> Unit
) {
    val swingColors = SwingColors()
    val appColors = if (!JBColor.isBright()) appDarkColors else appLightColors

    PlaygroundTheme(
        colors = appColors.copy(
            background = swingColors.background,
            onBackground = swingColors.onBackground,
            surface = swingColors.surface,
            onSurface = swingColors.onSurface,
        ),
        content = content
    )
}