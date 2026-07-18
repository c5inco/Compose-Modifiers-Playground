import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        startApp()
    }
}
