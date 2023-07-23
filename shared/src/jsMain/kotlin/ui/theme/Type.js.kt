package ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.platform.Typeface
import org.jetbrains.skia.Data
import org.jetbrains.skia.Typeface
import org.jetbrains.skiko.loadBytesFromPath

actual suspend fun editorTypography(): Typography = Typography(
    defaultFontFamily = loadCustomFonts()
)

suspend fun loadCustomFonts(): FontFamily {
    val fontBytes = loadBytesFromPath("jetbrainsmono_regular.ttf")
    val skTypeface: Typeface = Typeface.makeFromData(Data.makeFromBytes(fontBytes))
    return FontFamily(Typeface(skTypeface))
}