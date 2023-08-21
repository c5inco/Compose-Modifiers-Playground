package ui

import androidx.compose.foundation.Image
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import data.AvailableContentAlphas

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
    val ca = when (alpha) {
        AvailableContentAlphas.Medium -> ContentAlpha.medium
        AvailableContentAlphas.Disabled -> ContentAlpha.disabled
        else -> ContentAlpha.high
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
    // TODO: commonMain issue with painterResource
    // Image(
    //     painter = painterResource("images/$imageName"),
    //     contentDescription = "$imageName image",
    //     modifier = modifier
    // )
}