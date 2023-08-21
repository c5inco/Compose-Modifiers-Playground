package ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val blue200: Color = Color(0xff90caf9)
val blue300: Color = Color(0xff64b5f6)
val blue700: Color = Color(0xff1976d2)
val pink200: Color = Color(0xfff48fb1)
val pink400: Color = Color(0xffec407a)
val pink400Light: Color = Color(0xffff77a9)
val lightGray: Color = Color(0xfff2f2f2)

val Colors.secondarySurface: Color get() = if (isLight) secondary else surface

val appLightColors: Colors = lightColors(
    primary = pink400,
    secondary = pink400.copy(alpha = 0.5f),
    background = lightGray
)

val appDarkColors: Colors = darkColors(
    primary = pink200,
    secondary = pink200
)

// Code colors (dark theme)
object EditorColors {
    val backgroundDark: Color = Color(0xFF2B2B2B)
    val backgroundMedium: Color = Color(0xFF3C3F41)
    val backgroundLight: Color = Color(0xFF4E5254)
    val baseColor: Color = Color(0xFFA9B7C6)
    val keywordColor: Color = Color(0xFFCC7832)
    val functionColor: Color = Color(0xFFFFC66D)
    val commentColor: Color = Color(0xFF808080)
    val annotationColor: Color = Color(0xFFBBB529)
    val valueColor: Color = Color(0xFF6897BB)
    val punctuationColor: Color = Color(0xFFA1C17E)
    val numberColor: Color = Color(0xFF6897BB)
    val stringColor: Color = Color(0xFF6A8759)
    val namedArgumentColor: Color = Color(0xFF467CDA)
    val extensionColor: Color = Color(0xFF9876AA)
}