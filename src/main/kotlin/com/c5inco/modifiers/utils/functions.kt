package com.c5inco.modifiers.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.c5inco.modifiers.ui.EditorTheme

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