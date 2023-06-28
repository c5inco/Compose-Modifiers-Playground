import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        Window("Falling Balls") {
            Text("This is in the browser")
        }
    }
}