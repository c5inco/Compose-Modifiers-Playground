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