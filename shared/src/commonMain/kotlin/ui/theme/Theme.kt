package ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.*

@Composable
fun PlaygroundTheme(
    colors: Colors,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colors
    ) {
        content()
    }
}

@Composable
fun EditorTheme(
    content: @Composable () -> Unit
) {
    var typography by remember { mutableStateOf(Typography()) }

    LaunchedEffect(Unit) {
        typography = editorTypography()
    }

    MaterialTheme(typography = typography, content = content)
}