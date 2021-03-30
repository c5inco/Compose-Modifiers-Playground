package com.c5inco.modifiers.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.c5inco.modifiers.ui.EditorTheme
import kotlin.math.roundToInt

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

fun formatCode(str: String) = buildAnnotatedString {
    withStyle(EditorTheme.code.simple) {
        var strFormatted = str.replace("\t", "    ")
        append(strFormatted)
        addStyle(EditorTheme.code.simple, strFormatted, Regex("[\\[\\]\\(\\)\\.{}]"))
        addStyle(EditorTheme.code.punctuation, strFormatted, "=")
        addStyle(EditorTheme.code.punctuation, strFormatted, ":")
        addStyle(EditorTheme.code.namedArgument, strFormatted, Regex("[a-zA-z]+\\s="))
        addStyle(EditorTheme.code.keyword, strFormatted, Regex("[a-zA-z]+\\.]"))
        addStyle(EditorTheme.code.function, strFormatted, Regex("[a-zA-z]+\\("))
        addStyle(EditorTheme.code.function, strFormatted, Regex("\\.[a-zA-z]+\\("))
        //addStyle(EditorTheme.code.composable, strFormatted, Regex("^\\s*[a-zA-z]+\\("))
        addStyle(EditorTheme.code.property, strFormatted, Regex("\\.[a-zA-z]+"))
        addStyle(EditorTheme.code.extension, strFormatted, Regex("(RectangleShape|CircleShape)[^\\(]"))
        addStyle(EditorTheme.code.value, strFormatted, "Modifier")
        addStyle(EditorTheme.code.number, strFormatted, Regex("(\\d+.dp)"))
        addStyle(EditorTheme.code.extension, strFormatted, Regex("(.dp|.sp)"))
        addStyle(EditorTheme.code.annotation, strFormatted, Regex("^@[a-zA-Z_]*"))
        addStyle(EditorTheme.code.comment, strFormatted, Regex("^\\s*//.*"))
        addStyle(EditorTheme.code.punctuation, strFormatted, ".")
        addStyle(EditorTheme.code.keyword, strFormatted, ",")
        addStyle(EditorTheme.code.string, strFormatted, "\"")
    }
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: String) {
    addStyle(style, text, Regex.fromLiteral(regexp))
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: Regex) {
    for (result in regexp.findAll(text)) {
        addStyle(style, result.range.first, result.range.last + 1)
    }
}

@Composable
fun DotsBackground(modifier: Modifier) {
    Canvas(
        modifier.fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val circleColor = SolidColor(Color.LightGray)
        val circleRadius = 2f
        val circleStep = 20

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