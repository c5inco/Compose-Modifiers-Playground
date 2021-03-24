package com.c5inco.modifiers.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

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