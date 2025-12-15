package ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PlaygroundTheme(
    colors: Colors = appLightColors,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colors,
        typography = appTypography()
    ) {
        content()
    }
}

@Composable
fun EditorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = editorTypography()
    ) {
        content()
    }
}