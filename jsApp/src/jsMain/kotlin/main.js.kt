import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.dp
import data.Templates
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import ui.Playground
import ui.theme.PlaygroundTheme
import utils.withTouchSlop

fun main() {
    console.log("startup")

    onWasmReady {
        console.log("wasm loaded")
        document.getElementById("loadingIndicator")?.setAttribute("style", "display: none")
        BrowserViewportWindow("Compose Modifiers Playground") {
            val defaultTemplate = Templates.Sun
            var activeTemplate by remember { mutableStateOf(defaultTemplate) }

            // Decrease the touch slop. The default value of too high for desktop
            val vc = LocalViewConfiguration.current.withTouchSlop(
                with(LocalDensity.current) { 0.125.dp.toPx() },
            )

            CompositionLocalProvider(LocalViewConfiguration provides vc) {
                PlaygroundTheme {
                    Playground(
                        modifier = Modifier.fillMaxSize(),
                        activeTemplate = activeTemplate,
                        onTemplateChange = {
                            activeTemplate = it.copy()
                        }
                    )
                }
            }
        }
    }
}