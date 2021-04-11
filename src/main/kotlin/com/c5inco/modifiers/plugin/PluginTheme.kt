package com.c5inco.modifiers.plugin

import androidx.compose.runtime.Composable
import com.c5inco.modifiers.plugin.intellij.SwingColor
import com.c5inco.modifiers.ui.PlaygroundTheme
import com.c5inco.modifiers.ui.theme.appDarkColors
import com.c5inco.modifiers.ui.theme.appLightColors
import com.intellij.util.ui.UIUtil

@Composable
fun PluginTheme(
    content: @Composable () -> Unit
) {
    val swingColor = SwingColor()
    val appColors = if (UIUtil.isUnderDarcula()) appDarkColors else appLightColors

    PlaygroundTheme(
        colors = appColors.copy(
            background = swingColor.background,
            onBackground = swingColor.onBackground,
            surface = swingColor.surface,
            onSurface = swingColor.onSurface,
        ),
        content = content
    )
}