import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import data.Templates
import ui.Playground
import ui.theme.PlaygroundTheme

@OptIn(ExperimentalComposeUiApi::class)
fun main() =
    singleWindowApplication(
        title = "Compose Modifiers Playground",
        state = WindowState(size = DpSize(width = 1024.dp, height = 768.dp))
    ) {
        val defaultTemplate = Templates.Sun
        var activeTemplate by remember { mutableStateOf(defaultTemplate) }

        PlaygroundTheme {
            Playground(
                activeTemplate = activeTemplate,
                onTemplateChange = {
                    activeTemplate = it.copy()
                }
            )
        }
    }