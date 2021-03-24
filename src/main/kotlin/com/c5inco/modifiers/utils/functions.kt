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

fun codeString(str: String) = buildAnnotatedString {
    withStyle(EditorTheme.code.simple) {
        val strFormatted = str.replace("\t", "    ")
        append(strFormatted)
        addStyle(EditorTheme.code.punctuation, strFormatted, ":")
        addStyle(EditorTheme.code.punctuation, strFormatted, "=")
        addStyle(EditorTheme.code.punctuation, strFormatted, "\"")
        addStyle(EditorTheme.code.punctuation, strFormatted, "[")
        addStyle(EditorTheme.code.punctuation, strFormatted, "]")
        addStyle(EditorTheme.code.punctuation, strFormatted, "{")
        addStyle(EditorTheme.code.punctuation, strFormatted, "}")
        addStyle(EditorTheme.code.punctuation, strFormatted, "(")
        addStyle(EditorTheme.code.punctuation, strFormatted, ")")
        addStyle(EditorTheme.code.punctuation, strFormatted, ",")
        addStyle(EditorTheme.code.keyword, strFormatted, "fun ")
        addStyle(EditorTheme.code.keyword, strFormatted, "val ")
        addStyle(EditorTheme.code.keyword, strFormatted, "var ")
        addStyle(EditorTheme.code.keyword, strFormatted, "private ")
        addStyle(EditorTheme.code.keyword, strFormatted, "internal ")
        addStyle(EditorTheme.code.keyword, strFormatted, "for ")
        addStyle(EditorTheme.code.keyword, strFormatted, "expect ")
        addStyle(EditorTheme.code.keyword, strFormatted, "actual ")
        addStyle(EditorTheme.code.keyword, strFormatted, "import ")
        addStyle(EditorTheme.code.keyword, strFormatted, "package ")
        addStyle(EditorTheme.code.value, strFormatted, "true")
        addStyle(EditorTheme.code.value, strFormatted, "false")
        addStyle(EditorTheme.code.value, strFormatted, Regex("[0-9]*"))
        addStyle(EditorTheme.code.annotation, strFormatted, Regex("^@[a-zA-Z_]*"))
        addStyle(EditorTheme.code.comment, strFormatted, Regex("^\\s*//.*"))
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