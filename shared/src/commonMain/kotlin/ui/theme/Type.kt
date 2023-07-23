package ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import ui.theme.EditorColors.annotationColor
import ui.theme.EditorColors.baseColor
import ui.theme.EditorColors.commentColor
import ui.theme.EditorColors.extensionColor
import ui.theme.EditorColors.functionColor
import ui.theme.EditorColors.keywordColor
import ui.theme.EditorColors.namedArgumentColor
import ui.theme.EditorColors.numberColor
import ui.theme.EditorColors.punctuationColor
import ui.theme.EditorColors.stringColor
import ui.theme.EditorColors.valueColor

expect suspend fun editorTypography(): Typography

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