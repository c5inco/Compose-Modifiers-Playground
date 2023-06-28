import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import data.Templates
import org.jetbrains.skiko.wasm.onWasmReady
import ui.Playground

fun main() {
    onWasmReady {
        Window("Compose Modifiers Playground") {
            val defaultTemplate = Templates.Sun
            var activeTemplate by remember { mutableStateOf(defaultTemplate) }

            Playground(activeTemplate, onTemplateChange = {
                activeTemplate = it.copy()
            })
        }
    }
}