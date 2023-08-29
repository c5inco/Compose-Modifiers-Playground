@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package utils

import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.LocalWindow
import java.awt.Component
import java.awt.Window
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent

@Composable
actual fun calculateWindowSize(): DpSize {
    val window: Window? = LocalWindow.current

    var windowSize by remember(window) {
        mutableStateOf(window?.getDpSize() ?: DpSize.Zero)
    }

    // Add a listener and listen for componentResized events
    DisposableEffect(window) {
        val listener = object : ComponentAdapter() {
            override fun componentResized(event: ComponentEvent) {
                windowSize = window!!.getDpSize()
            }
        }

        window?.addComponentListener(listener)

        onDispose {
            window?.removeComponentListener(listener)
        }
    }

    return windowSize
}

private fun Component.getDpSize(): DpSize = DpSize(width.dp, height.dp)