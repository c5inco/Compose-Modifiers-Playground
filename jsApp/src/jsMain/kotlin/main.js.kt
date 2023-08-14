import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import data.Templates
import org.jetbrains.skiko.wasm.onWasmReady
import ui.Playground
import ui.theme.PlaygroundTheme

fun main() {
    onWasmReady {
        BrowserViewportWindow("Compose Modifiers Playground") {
            val defaultTemplate = Templates.Sun
            var activeTemplate by remember { mutableStateOf(defaultTemplate) }

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