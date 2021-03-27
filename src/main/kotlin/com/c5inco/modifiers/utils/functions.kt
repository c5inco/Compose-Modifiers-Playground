package com.c5inco.modifiers.utils

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