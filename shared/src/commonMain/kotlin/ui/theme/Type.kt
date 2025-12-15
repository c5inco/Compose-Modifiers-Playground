package ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import modifiersplayground.shared.generated.resources.Res
import modifiersplayground.shared.generated.resources.googleSansCode_Bold
import modifiersplayground.shared.generated.resources.googleSansCode_BoldItalic
import modifiersplayground.shared.generated.resources.googleSansCode_Italic
import modifiersplayground.shared.generated.resources.googleSansCode_Medium
import modifiersplayground.shared.generated.resources.googleSansCode_MediumItalic
import modifiersplayground.shared.generated.resources.googleSansCode_Regular
import modifiersplayground.shared.generated.resources.googleSans_Bold
import modifiersplayground.shared.generated.resources.googleSans_BoldItalic
import modifiersplayground.shared.generated.resources.googleSans_Italic
import modifiersplayground.shared.generated.resources.googleSans_Medium
import modifiersplayground.shared.generated.resources.googleSans_MediumItalic
import modifiersplayground.shared.generated.resources.googleSans_Regular
import org.jetbrains.compose.resources.Font
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

@Composable
fun appTypography(): Typography {
    val googleSans = FontFamily(
        Font(Res.font.googleSans_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.googleSans_Italic, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.googleSans_Bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.googleSans_BoldItalic, FontWeight.Bold, FontStyle.Italic),
        Font(Res.font.googleSans_Medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.googleSans_MediumItalic, FontWeight.Medium, FontStyle.Italic)
    )
    return Typography(defaultFontFamily = googleSans)
}

@Composable
fun editorTypography(): Typography {
    val googleSansCode = FontFamily(
        Font(Res.font.googleSansCode_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.googleSansCode_Italic, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.googleSansCode_Bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.googleSansCode_BoldItalic, FontWeight.Bold, FontStyle.Italic),
        Font(Res.font.googleSansCode_Medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.googleSansCode_MediumItalic, FontWeight.Medium, FontStyle.Italic)
    )
    return Typography(defaultFontFamily = googleSansCode)
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
