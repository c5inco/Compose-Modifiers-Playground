import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import data.Templates
import org.jetbrains.skiko.wasm.onWasmReady
import ui.Playground

fun main() {
    onWasmReady {
        BrowserViewportWindow("Compose Modifiers Playground") {
            val defaultTemplate = Templates.Sun
            var activeTemplate by remember { mutableStateOf(defaultTemplate) }

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