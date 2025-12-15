package ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import modifiersplayground.shared.generated.resources.Res
import modifiersplayground.shared.generated.resources.inter_regular
import modifiersplayground.shared.generated.resources.jetbrainsmono_bold
import modifiersplayground.shared.generated.resources.jetbrainsmono_bold_italic
import modifiersplayground.shared.generated.resources.jetbrainsmono_extrabold
import modifiersplayground.shared.generated.resources.jetbrainsmono_extrabold_italic
import modifiersplayground.shared.generated.resources.jetbrainsmono_italic
import modifiersplayground.shared.generated.resources.jetbrainsmono_medium
import modifiersplayground.shared.generated.resources.jetbrainsmono_medium_italic
import modifiersplayground.shared.generated.resources.jetbrainsmono_regular
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
    val inter = FontFamily(
        Font(Res.font.inter_regular, FontWeight.Normal, FontStyle.Normal)
    )
    return Typography(defaultFontFamily = inter)
}

@Composable
fun editorTypography(): Typography {
    val jetbrainsMono = FontFamily(
        Font(Res.font.jetbrainsmono_regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.jetbrainsmono_italic, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.jetbrainsmono_bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.jetbrainsmono_bold_italic, FontWeight.Bold, FontStyle.Italic),
        Font(Res.font.jetbrainsmono_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
        Font(Res.font.jetbrainsmono_extrabold_italic, FontWeight.ExtraBold, FontStyle.Italic),
        Font(Res.font.jetbrainsmono_medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.jetbrainsmono_medium_italic, FontWeight.Medium, FontStyle.Italic)
    )
    return Typography(defaultFontFamily = jetbrainsMono)
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
