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

@OptIn(ExperimentalComposeUiApi::class)
fun main() =
    singleWindowApplication(
        title = "Compose Modifiers Playground",
        state = WindowState(size = DpSize(800.dp, 800.dp))
    ) {
        val defaultTemplate = Templates.Sun
        var activeTemplate by remember { mutableStateOf(defaultTemplate) }

        Playground(activeTemplate, onTemplateChange = {
            activeTemplate = it.copy()
        })
    }