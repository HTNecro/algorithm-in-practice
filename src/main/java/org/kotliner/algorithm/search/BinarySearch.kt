package org.kotliner.algorithm.search

fun binarySearch(array: MutableList<Int>, value: Int): Int? {
    var start = 0
    var end = array.size - 1

    while (start <= end) {
        val binary = start + (end - start) / 2
        when {
            value == array[binary] -> return binary
            value < array[binary] -> end = binary - 1
            else -> start = binary + 1
        }
    }
    return null
}

fun main() {
    val array = mutableListOf(1, 2, 5, 6, 7, 8, 9)
    val index = binarySearch(array, 5)
    assert(index == 2)
}