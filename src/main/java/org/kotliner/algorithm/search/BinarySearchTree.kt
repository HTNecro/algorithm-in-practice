package org.kotliner.algorithm.search

class BinarySearchTree {

    private var root: Node? = null

    data class Node(
        val key: String,
        var value: Any,
        var left: Node? = null,
        var right: Node? = null
    )

    fun printNodes() {
        printNodes(root)
    }

    private fun printNodes(x: Node?) {
        x?.let {
            printNodes(x.left)
            println("key: ${x.key}, value: ${x.value}")
            printNodes(x.right)
        }
    }

    fun get(key: String): Any? {
        return get(root, key)
    }

    private tailrec fun get(x: Node?, key: String): Any? {
        if (x == null) return null
        return when {
            key < x.key -> get(x.left, key)
            key > x.key -> get(x.right, key)
            else -> x.value
        }
    }

    fun put(key: String, value: Any) {
        root = put(root, key, value)
    }

    private fun put(x: Node?, key: String, value: Any): Node {
        if (x == null) {
            return Node(key = key, value = value)
        }
        when {
            key < x.key -> x.left = put(x.left, key, value)
            key > x.key -> x.right = put(x.right, key, value)
            else -> x.value = value
        }
        return x
    }

    fun min(): String? {
        return min(root)?.key
    }

    private fun min(x: Node?): Node? {
        return if (x?.left == null) x else min(x.left)
    }


    fun max(): String? {
        return max(root)?.key
    }

    private fun max(x: Node?): Node? {
        return if (x?.right == null) x else max(x.right)
    }

    fun deleteMin() {
        root = deleteMin(root)
    }

    private fun deleteMin(x: Node?): Node? {
        x?.left ?: return x?.right
        x.left = deleteMin(x.left)
        return x
    }

    fun deleteMax() {
        root = deleteMax(root)
    }

    private fun deleteMax(x: Node?): Node? {
        x?.right ?: return x?.left
        x.right = deleteMin(x.right)
        return x
    }

    fun delete(key: String) {
        root = delete(root, key)
    }

    private fun delete(x: Node?, key: String): Node? {
        x ?: return null
        when {
            key < x.key -> x.left = delete(x.left, key)
            key > x.key -> x.right = delete(x.right, key)
            else -> {
                if (x.right == null) {
                    return x.left
                }
                if (x.left == null) {
                    return x.right
                }
                val node = min(x.right)
                node?.right = deleteMin(x.right)
                node?.left = x.left
                return node
            }
        }
        return x
    }
}

fun main() {
    val bst = BinarySearchTree()
    bst.put("3", 30)
    bst.put("1", 10)
    bst.put("2", 20)
    bst.put("2", 22)
    bst.put("5", 55)
    bst.put("7", 55)
    bst.printNodes()

    println()

    println("key: 2, get value: ${bst.get("2")}")
    println("min: ${bst.min()}")
    println("max: ${bst.max()}")

    println()

    bst.deleteMin()
    println("delete min")
    bst.printNodes()

    println()

    bst.deleteMax()
    println("delete max")
    bst.printNodes()

    println()

    bst.delete("3")
    println("delete node 3")
    bst.printNodes()

    println()

    bst.delete("7")
    println("delete node 7")
    bst.printNodes()
}