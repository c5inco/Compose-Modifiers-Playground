package com.c5inco.modifiers.ui

import androidx.compose.foundation.Image
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorXmlResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.c5inco.modifiers.data.AvailableContentAlphas

@Composable
fun EmojiChildElement(
    emoji: String,
    modifier: Modifier
) {
    Text(emoji, fontSize = 48.sp, modifier = modifier)
}

@Composable
fun TextChildElement(
    text: String,
    style: TextStyle,
    color: Color = LocalContentColor.current,
    alpha: AvailableContentAlphas,
    modifier: Modifier
) {
    var ca = ContentAlpha.high

    when (alpha) {
        AvailableContentAlphas.Medium -> {
            ca = ContentAlpha.medium
        }
        AvailableContentAlphas.Disabled -> {
            ca = ContentAlpha.disabled
        }
    }

    Text(
        text,
        style = style,
        color = color.copy(alpha = ca),
        modifier = modifier
    )
}

@Composable
fun ImageChildElement(
    imageName: String,
    modifier: Modifier
) {
    Image(
        imageVector = vectorXmlResource("images/$imageName"),
        contentDescription = "$imageName image",
        modifier = modifier
    )
}