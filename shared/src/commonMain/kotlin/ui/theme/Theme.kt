package ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

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