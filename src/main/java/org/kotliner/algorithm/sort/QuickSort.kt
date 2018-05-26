package org.kotliner.algorithm.sort

fun partition(array: MutableList<Int>, start: Int, end: Int): Int {
    var i = start
    var j = end
    val pivot = array[start]

    while (i <= j) {
        while (array[i] < pivot) {
            i++
        }
        while (array[j] > pivot) {
            j--
        }
        if (i <= j) {
            val temp = array[i]
            array[i] = array[j]
            array[j] = temp
            i++
            j--
        }
    }
    return i
}

fun quickSort(array: MutableList<Int>, start: Int, end: Int) {
    val index = partition(array, start, end)
    if (start < index - 1) {
        quickSort(array, start, index - 1)
    }
    if (end > index) {
        quickSort(array, index, end)
    }
}

fun main(args: Array<String>) {
    val array = mutableListOf(5, 7, 8, 1, 2, 5, 6, 9)
    quickSort(array, 0, array.size - 1)
    println(array)
}