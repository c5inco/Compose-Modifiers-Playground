import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
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
        @OptIn(ExperimentalComposeUiApi::class)
        CanvasBasedWindow(
            title = "Compose Modifiers Playground",
            canvasElementId = "ComposeTarget"
        ) {
            var activeTemplate by remember { mutableStateOf(Templates.Rainbow) }

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