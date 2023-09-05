import androidx.compose.foundation.isSystemInDarkTheme
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
import ui.theme.appDarkColors
import ui.theme.appLightColors
import utils.withTouchSlop

fun main() {
    console.log("startup")

    onWasmReady {
        console.log("wasm loaded")
        document.getElementById("loadingIndicator")?.setAttribute("style", "display: none")
        BrowserViewportWindow("Compose Modifiers Playground") {
            var activeTemplate by remember { mutableStateOf(Templates.Rainbow) }

            // Note: isSystemInDarkTheme() is not yet implemented in JS. It will always return false until
            // the feature is implemented.
            val isSystemInDarkTheme = isSystemInDarkTheme()
            var useDarkTheme by remember { mutableStateOf(isSystemInDarkTheme) }
            val themeColors = if (useDarkTheme) appDarkColors else appLightColors

            // Decrease the touch slop. The default value of too high for desktop
            val vc = LocalViewConfiguration.current.withTouchSlop(
                with(LocalDensity.current) { 0.125.dp.toPx() },
            )

            CompositionLocalProvider(LocalViewConfiguration provides vc) {
                PlaygroundTheme(colors = themeColors) {
                    Playground(
                        modifier = Modifier.fillMaxSize(),
                        activeTemplate = activeTemplate,
                        onTemplateChange = {
                            activeTemplate = it.copy()
                        },
                        darkModeSupported = true,
                        darkMode = useDarkTheme,
                        onDarkModeChange = {
                            // TODO: Find out why toggling dark mode on or off doesn't work
                            useDarkTheme = it
                        }
                    )
                }
            }
        }
    }
}