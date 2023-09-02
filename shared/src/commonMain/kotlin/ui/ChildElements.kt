package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.AvailableContentAlphas
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.icons.AppIcons
import ui.icons.Placeholder

@Composable
fun EmojiChildElement(
    emoji: String,
    modifier: Modifier
) {
    Text(emoji, fontSize = 48.sp, modifier = modifier)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ImageEmojiChildElement(
    emoji: ImageEmoji,
    modifier: Modifier
) {
    Image(
        painter = painterResource("images/$emoji.png"),
        contentDescription = null,
        modifier = modifier.size(48.dp)
    )
}

enum class ImageEmoji {
    Avocado,
    HotBeverage,
    Robot
}

fun convertImageEmojiToEmoji(image: ImageEmoji): String {
    when (image) {
        ImageEmoji.Avocado ->
            return "🥑"
        ImageEmoji.HotBeverage ->
            return "☕"
        ImageEmoji.Robot ->
            return "🤖"
    }
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
    Image(
        imageVector = AppIcons.Placeholder,
        contentDescription = "$imageName image",
        modifier = modifier
    )
}