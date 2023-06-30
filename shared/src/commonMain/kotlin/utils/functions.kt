package utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import ui.theme.EditorStyles
import kotlin.math.roundToInt

data class DpRect(
    val top: Dp,
    val left: Dp,
    val right: Dp,
    val bottom: Dp,
)

@Composable
fun Rect.toDp(): DpRect {
    return with(LocalDensity.current) {
        DpRect(top.toDp(), left.toDp(), right.toDp(), bottom.toDp())
    }
}

@Composable
fun Offset.toDp(): DpOffset {
    return with(LocalDensity.current) {
        DpOffset(x = x.toDp(), y = y.toDp())
    }
}

@Composable
fun IntSize.toDp(): DpSize {
    return with(LocalDensity.current) {
        DpSize(width = width.toDp(), height = height.toDp())
    }
}

fun Float.roundToDecimals(decimals: Int): Float {
    var dotAt = 1
    repeat(decimals) { dotAt *= 10 }
    val roundedValue = (this * dotAt).roundToInt()
    return (roundedValue / dotAt) + (roundedValue % dotAt).toFloat() / dotAt
}

fun <T> chunk(list: List<T>, size: Int): List<List<T>> {
    val chunkedList = mutableListOf<List<T>>()

    for (i in 0..list.size step size) {
        var chunkIndex = i + size
        if (chunkIndex > list.size) {
            chunkIndex = i + (list.size % size)
        }

        chunkedList.add(list.slice(i until chunkIndex))
    }

    return chunkedList.toList()
}

fun until(start: Int, end: Int, incrementStep: Int, body: (idx: Int) -> Unit) {
    for (i in start until end step incrementStep) {
        body(i)
    }
}

fun downTo(start: Int, end: Int, incrementStep: Int, body: (idx: Int) -> Unit) {
    for (i in start downTo end step incrementStep) {
        body(i)
    }
}

fun rgba2rgb(rgbBg: Color, rgbFg: Color, alpha: Float): Color {
    return Color(
        (1 - alpha) * rgbBg.red + alpha * rgbFg.red,
        (1 - alpha) * rgbBg.green + alpha * rgbFg.green,
        (1 - alpha) * rgbBg.blue + alpha * rgbFg.blue
    )
}

fun formatCode(str: String) = buildAnnotatedString {
    withStyle(EditorStyles.simple) {
        var strFormatted = str.replace("\t", "    ")
        append(strFormatted)

        addStyle(EditorStyles.punctuation, strFormatted, "=")
        addStyle(EditorStyles.punctuation, strFormatted, ":")
        addStyle(EditorStyles.namedArgument, strFormatted, Regex("[a-zA-z]+\\s="))
        addStyle(EditorStyles.keyword, strFormatted, Regex("[a-zA-z]+\\."))
        addStyle(EditorStyles.function, strFormatted, Regex("[a-zA-z]+\\("))
        addStyle(EditorStyles.function, strFormatted, Regex("\\.[a-zA-z]+\\("))
        addStyle(EditorStyles.property, strFormatted, Regex("\\.[a-zA-z]+"))
        addStyle(EditorStyles.simple, strFormatted, Regex("[\\[\\]\\(\\)\\.\\{\\}]"))
        addStyle(EditorStyles.extension, strFormatted, Regex("(RectangleShape|CircleShape)[^\\(]"))
        addStyle(EditorStyles.value, strFormatted, "Modifier")
        addStyle(EditorStyles.number, strFormatted, Regex("(-*\\df*)"))
        addStyle(EditorStyles.extension, strFormatted, Regex("(\\.dp|\\.sp)"), 1)
        addStyle(EditorStyles.annotation, strFormatted, Regex("^@[a-zA-Z_]+"))
        addStyle(EditorStyles.comment, strFormatted, Regex("^\\s*//.*"))
        addStyle(EditorStyles.keyword, strFormatted, ",")
        addStyle(EditorStyles.string, strFormatted, "\"")
    }
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: String, skip: Int = 0) {
    addStyle(style, text, Regex.fromLiteral(regexp), skip)
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: Regex, skip: Int = 0) {
    for (result in regexp.findAll(text)) {
        addStyle(style, result.range.first + skip, result.range.last + 1)
    }
}

@Composable
fun DotsBackground(modifier: Modifier) {
    val contentColor = rgba2rgb(
        MaterialTheme.colors.background,
        MaterialTheme.colors.onBackground,
        0.15f
    )
    val density = LocalDensity.current.density

    Canvas(
        modifier.fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val circleColor = SolidColor(contentColor)
        val circleRadius = 2f * density
        val circleStep = (20 * density).toInt()

        val circle: (Int, Int) -> Unit = { x, y ->
            drawCircle(
                brush = circleColor,
                radius = circleRadius,
                center = Offset(x = x.toFloat(), y = y.toFloat())
            )
        }

        until((canvasWidth / 2).toInt(), canvasWidth.toInt(), circleStep) { uidx ->
            downTo((canvasHeight / 2).toInt(), 0, circleStep) { didx ->
                circle(uidx, didx)
            }
            until((canvasHeight / 2).toInt(), canvasHeight.toInt(), circleStep) { uidx2 ->
                circle(uidx, uidx2)
            }
        }
        downTo((canvasWidth / 2).toInt(), 0, circleStep) { uidx ->
            downTo((canvasHeight / 2).toInt(), 0, circleStep) { didx ->
                circle(uidx, didx)
            }
            until((canvasHeight / 2).toInt(), canvasHeight.toInt(), circleStep) { uidx2 ->
                circle(uidx, uidx2)
            }
        }
    }
}

@Composable
fun DottedLine(modifier: Modifier = Modifier, color: Color = Color.Gray) {
    Canvas(
        modifier
            .fillMaxWidth()
    ) {
        until(0, size.width.toInt(), 10) {
            drawCircle(
                brush = SolidColor(color),
                radius = 1f,
                center = Offset(x = it.toFloat(), y = 0f)
            )
        }
    }
}