package com.c5inco.modifiers

import androidx.compose.desktop.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.c5inco.modifiers.ui.PlaygroundTheme
import com.c5inco.modifiers.ui.theme.appDarkColors
import com.c5inco.modifiers.ui.theme.appLightColors
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val image = getWindowIcon()
    Window(
        title = "Modifiers Playground",
        size = IntSize(width = 1100, height = 800),
        icon = image
    ) {
        var darkTheme by remember { mutableStateOf(false) }
        PlaygroundTheme(colors = if (darkTheme) appLightColors else appDarkColors) {
            Box(Modifier.fillMaxSize()) {
                Application()
                Column(
                    Modifier
                        .size(24.dp)
                        .clickable {
                            darkTheme = !darkTheme
                        }
                ) { }
            }
        }
    }
}

fun getWindowIcon(): BufferedImage {
    var image: BufferedImage? = null
    try {
        image = ImageIO.read(File("src/main/resources/META-INF/macosicon.png"))
    } catch (e: Exception) {
        // image file does not exist
        println("Failed to load icon")
    }

    if (image == null) {
        image = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
    }

    return image
}