import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import data.Templates
import ui.Playground
import ui.theme.PlaygroundTheme
import utils.withTouchSlop

fun main() =
    singleWindowApplication(
        title = "Compose Modifiers Playground",
        state = WindowState(size = DpSize(width = 1024.dp, height = 768.dp))
    ) {
        val defaultTemplate = Templates.Sun
        var activeTemplate by remember { mutableStateOf(defaultTemplate) }

        // Decrease the touch slop. The default value of too high for desktop
        val vc = LocalViewConfiguration.current.withTouchSlop(
            with(LocalDensity.current) { 0.125.dp.toPx() },
        )

        CompositionLocalProvider(LocalViewConfiguration provides vc) {
            PlaygroundTheme {
                Playground(
                    activeTemplate = activeTemplate,
                    onTemplateChange = {
                        activeTemplate = it.copy()
                    }
                )
            }
        }
    }