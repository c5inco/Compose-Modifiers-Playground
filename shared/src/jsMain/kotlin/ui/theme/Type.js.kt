package ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.platform.Typeface
import org.jetbrains.skia.Data
import org.jetbrains.skia.Typeface
import org.jetbrains.skiko.loadBytesFromPath

actual suspend fun appTypography(): Typography = Typography(
    defaultFontFamily = loadCustomFonts("inter_regular")
)

actual suspend fun editorTypography(): Typography = Typography(
    defaultFontFamily = loadCustomFonts("jetbrainsmono_regular")
)

suspend fun loadCustomFonts(fontName: String): FontFamily {
    val fontBytes = loadBytesFromPath("$fontName.ttf")
    val skTypeface: Typeface = Typeface.makeFromData(Data.makeFromBytes(fontBytes))
    return FontFamily(Typeface(skTypeface))
}