package com.c5inco.modifiers

import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import com.c5inco.modifiers.ui.PlaygroundTheme
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
        PlaygroundTheme(colors = appLightColors) {
            Application()
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