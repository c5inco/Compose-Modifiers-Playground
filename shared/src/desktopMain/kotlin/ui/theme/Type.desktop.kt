package ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font as PlatformFont

actual suspend fun appTypography(): Typography = Typography()
actual suspend fun editorTypography(): Typography = Typography(
    defaultFontFamily = jetbrainsMono()
)

private fun Font(
    res: String,
    weight: FontWeight,
    style: FontStyle,
): Font =
    PlatformFont("font/$res.ttf", weight, style)

private fun jetbrainsMono() = FontFamily(
    Font(
        "jetbrainsmono_regular",
        FontWeight.Normal,
        FontStyle.Normal
    ),
    Font(
        "jetbrainsmono_italic",
        FontWeight.Normal,
        FontStyle.Italic
    ),

    Font(
        "jetbrainsmono_bold",
        FontWeight.Bold,
        FontStyle.Normal
    ),
    Font(
        "jetbrainsmono_bold_italic",
        FontWeight.Bold,
        FontStyle.Italic
    ),
    Font(
        "jetbrainsmono_extrabold",
        FontWeight.ExtraBold,
        FontStyle.Normal
    ),
    Font(
        "jetbrainsmono_extrabold_italic",
        FontWeight.ExtraBold,
        FontStyle.Italic
    ),

    Font(
        "jetbrainsmono_medium",
        FontWeight.Medium,
        FontStyle.Normal
    ),
    Font(
        "jetbrainsmono_medium_italic",
        FontWeight.Medium,
        FontStyle.Italic
    )
)