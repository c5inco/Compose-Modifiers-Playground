package com.c5inco.modifiers.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val blue200: Color = Color(0xff90caf9)
val blue300: Color = Color(0xff64b5f6)
val blue700: Color = Color(0xff1976d2)

val hoverLightColor: Color = Color.Black.copy(alpha = 0.1f)
val hoverDarkColor: Color = Color.White.copy(alpha = 0.1f)

// Code colors (dark theme)
val baseColor: Color = Color(0xFFA9B7C6)
val stringColor: Color = Color(0xFF6A8759)
val extensionColor: Color = Color(0xFF9876AA)

val appLightColors: Colors = lightColors(
    primary = blue700,
    secondary = blue200
)

val appDarkColors: Colors = darkColors(
)