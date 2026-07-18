package utils

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import kotlinx.browser.window
import org.w3c.dom.Window
import org.w3c.dom.events.Event

@Composable
actual fun calculateWindowSize(): DpSize {
    val density = LocalDensity.current

    var windowSize by remember {
        mutableStateOf(window.getDpSize(density))
    }

    // Add a listener and listen for resize events
    DisposableEffect(density) {
        val callback: (Event) -> Unit = {
            windowSize = window.getDpSize(density)
        }

        window.addEventListener("resize", callback)

        onDispose {
            window.removeEventListener("resize", callback)
        }
    }

    return windowSize
}

private fun Window.getDpSize(density: Density): DpSize = with(density) {
    DpSize(innerWidth.toDp(), innerHeight.toDp())
}