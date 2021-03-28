package com.c5inco.modifiers.ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import com.c5inco.modifiers.ui.theme.baseColor
import com.c5inco.modifiers.ui.theme.extensionColor
import com.c5inco.modifiers.ui.theme.stringColor

@Composable
fun PlaygroundTheme(
    colors: Colors = MaterialTheme.colors,
    typography: Typography = MaterialTheme.typography,
    shapes: Shapes = MaterialTheme.shapes,
    content: @Composable () -> Unit
) = DesktopMaterialTheme(
    colors,
    typography,
    shapes
) {
    content()
}

object EditorTheme {
    val colors: Colors = Colors()

    val code: Code = Code()

    class Colors(
        val backgroundDark: Color = Color(0xFF2B2B2B),
        val backgroundMedium: Color = Color(0xFF3C3F41),
        val backgroundLight: Color = Color(0xFF4E5254),

        val material: androidx.compose.material.Colors = darkColors(
            background = backgroundDark,
            surface = backgroundMedium,
            primary = Color.White
        ),
    )

    class Code(
        val simple: SpanStyle = SpanStyle(baseColor),
        val value: SpanStyle = SpanStyle(Color(0xFF6897BB)),
        val keyword: SpanStyle = SpanStyle(Color(0xFFCC7832)),
        val namedArgument: SpanStyle = SpanStyle(Color(0xFF467CDA)),
        val string: SpanStyle = SpanStyle(stringColor),
        val function: SpanStyle =
            SpanStyle(
                Color(0xFFFFC66D),
                fontStyle = FontStyle.Italic
            ),
        val composable: SpanStyle =
            SpanStyle(
                baseColor,
                fontStyle = FontStyle.Italic
            ),
        val number: SpanStyle = SpanStyle(Color(0xFF6897BB)),
        val punctuation: SpanStyle = SpanStyle(Color(0xFFA1C17E)),
        val annotation: SpanStyle = SpanStyle(Color(0xFFBBB529)),
        val comment: SpanStyle = SpanStyle(Color(0xFF808080)),
        val property: SpanStyle = SpanStyle(extensionColor),
        val extension: SpanStyle =
            SpanStyle(
                extensionColor,
                fontStyle = FontStyle.Italic
            ),
    )
}