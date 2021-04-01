package com.c5inco.modifiers.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.c5inco.modifiers.ui.theme.EditorColors.annotationColor
import com.c5inco.modifiers.ui.theme.EditorColors.baseColor
import com.c5inco.modifiers.ui.theme.EditorColors.commentColor
import com.c5inco.modifiers.ui.theme.EditorColors.extensionColor
import com.c5inco.modifiers.ui.theme.EditorColors.functionColor
import com.c5inco.modifiers.ui.theme.EditorColors.keywordColor
import com.c5inco.modifiers.ui.theme.EditorColors.namedArgumentColor
import com.c5inco.modifiers.ui.theme.EditorColors.numberColor
import com.c5inco.modifiers.ui.theme.EditorColors.punctuationColor
import com.c5inco.modifiers.ui.theme.EditorColors.stringColor
import com.c5inco.modifiers.ui.theme.EditorColors.valueColor

@Composable
fun Font(name: String, res: String, weight: FontWeight, style: FontStyle): Font =
    androidx.compose.ui.text.platform.Font("font/$res.ttf", weight, style)

object Fonts {
    @Composable
    fun jetbrainsMono() = FontFamily(
        Font(
            "JetBrains Mono",
            "jetbrainsmono_regular",
            FontWeight.Normal,
            FontStyle.Normal
        ),
        Font(
            "JetBrains Mono",
            "jetbrainsmono_italic",
            FontWeight.Normal,
            FontStyle.Italic
        ),

        Font(
            "JetBrains Mono",
            "jetbrainsmono_bold",
            FontWeight.Bold,
            FontStyle.Normal
        ),
        Font(
            "JetBrains Mono",
            "jetbrainsmono_bold_italic",
            FontWeight.Bold,
            FontStyle.Italic
        ),

        Font(
            "JetBrains Mono",
            "jetbrainsmono_extrabold",
            FontWeight.ExtraBold,
            FontStyle.Normal
        ),
        Font(
            "JetBrains Mono",
            "jetbrainsmono_extrabold_italic",
            FontWeight.ExtraBold,
            FontStyle.Italic
        ),

        Font(
            "JetBrains Mono",
            "jetbrainsmono_medium",
            FontWeight.Medium,
            FontStyle.Normal
        ),
        Font(
            "JetBrains Mono",
            "jetbrainsmono_medium_italic",
            FontWeight.Medium,
            FontStyle.Italic
        )
    )
}

object EditorStyles {
    val simple: SpanStyle = SpanStyle(baseColor)
    val value: SpanStyle = SpanStyle(valueColor)
    val keyword: SpanStyle = SpanStyle(keywordColor)
    val namedArgument: SpanStyle = SpanStyle(namedArgumentColor)
    val string: SpanStyle = SpanStyle(stringColor)
    val function: SpanStyle =
        SpanStyle(
            functionColor,
            fontStyle = FontStyle.Italic
        )
    val composable: SpanStyle =
        SpanStyle(
            baseColor,
            fontStyle = FontStyle.Italic
        )
    val number: SpanStyle = SpanStyle(numberColor)
    val punctuation: SpanStyle = SpanStyle(punctuationColor)
    val annotation: SpanStyle = SpanStyle(annotationColor)
    val comment: SpanStyle = SpanStyle(commentColor)
    val property: SpanStyle = SpanStyle(extensionColor)
    val extension: SpanStyle =
        SpanStyle(
            extensionColor,
            fontStyle = FontStyle.Italic
        )
}