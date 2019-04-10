package org.kotliner.algorithm.search

class RedBlackTree {

    companion object {
        const val RED = true
        const val BLACK = false
    }

    private var root: Node? = null

    data class Node(
        val key: String,
        var color: Boolean,
        var value: Any,
        var left: Node? = null,
        var right: Node? = null
    )

    private fun Node?.isRed(): Boolean {
        return this?.color == RED
    }


    fun preOrderPrintNodes() {
        preOrderPrintNodes(root)
    }

    private fun preOrderPrintNodes(x: Node?) {
        x?.let {
            println("key: ${x.key}, value: ${x.value}, color: ${x.color}")
            preOrderPrintNodes(x.left)
            preOrderPrintNodes(x.right)
        }
    }

    fun get(key: String): Any? {
        return get(root, key)
    }

    private tailrec fun get(x: Node?, key: String): Any? {
        x ?: return null
        return when {
            key < x.key -> get(x.left, key)
            key > x.key -> get(x.right, key)
            else -> x.value
        }
    }


    private fun rotateLeft(h: Node?): Node? {
        h ?: return null

        val x = h.right
        h.right = x?.left
        x?.left = h

        x?.color = h.color
        h.color = RED
        return x
    }

    private fun rotateRight(h: Node?): Node? {
        h ?: return null

        val x = h.left
        h.left = x?.right
        x?.right = h

        x?.color = h.color
        h.color = RED
        return x
    }


    private fun flipColors(h: Node?) {
        h?.color = RED
        h?.left?.color = BLACK
        h?.right?.color = BLACK
    }


    fun put(key: String, value: Any) {
        root = put(root, key, value)
        root?.color = BLACK
    }

    private fun put(h: Node?, key: String, value: Any): Node? {
        h ?: return Node(key = key, value = value, color = RED)
        when {
            key < h.key -> h.left = put(h.left, key, value)
            key > h.key -> h.right = put(h.right, key, value)
            else -> {
                h.value = value
            }
        }
        var x = h
        if (!x.left.isRed() && x.right.isRed()) {
            x = rotateLeft(x)
        }
        if (x?.left.isRed() && x?.left?.left.isRed()) {
            x = rotateRight(x)
        }
        if (x?.left.isRed() && x?.right.isRed()) {
            flipColors(x)
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


    private fun balance(h: Node?): Node? {
        var x = h
        if (!x?.left.isRed() && x?.right.isRed()) {
            x = rotateLeft(x)
        }
        if (x?.left.isRed() && x?.left?.left.isRed()) {
            x = rotateRight(x)
        }
        if (x?.left.isRed() && x?.right.isRed()) {
            flipColors(x)
        }
        if (x?.right.isRed()) {
            x = rotateLeft(x)
        }
        return x
    }

    private fun moveRedLeft(h: Node?): Node? {
        var x = h
        flipColors2(x)
        if (x?.right?.left.isRed()) {
            x?.right = rotateRight(x?.right)
            x = rotateLeft(x)
        }
        return x
    }

    private fun flipColors2(h: Node?) {
        h?.color = BLACK
        h?.left?.color = RED
        h?.right?.color = RED
    }

    private fun moveRedRight(h: Node?): Node? {
        flipColors2(h)
        if (h?.left?.left.isRed()) {
            h?.right = rotateRight(h?.right)
        }
        return h
    }

    fun deleteMin() {
        if (!root?.left.isRed() && !root?.right.isRed()) {
            root?.color = RED
        }
        root = deleteMin(root)
        root?.apply { color = BLACK }
    }

    private fun deleteMin(h: Node?): Node? {
        h?.left ?: return null
        var x = h
        if (!x.left.isRed() && !x.left?.left.isRed()) {
            x = moveRedLeft(x)
        }
        x?.left = deleteMin(x?.left)
        return balance(x)
    }


    fun deleteMax() {
        if (!root?.left.isRed() && !root?.right.isRed()) {
            root?.color = RED
        }
        root = deleteMax(root)
        root?.apply { color = BLACK }
    }

    private fun deleteMax(h: Node?): Node? {
        var x = h
        if (x?.left.isRed()) {
            x = rotateRight(x)
        }
        x?.right ?: return null
        if (!x.right.isRed() && !x.right?.left.isRed()) {
            x = moveRedRight(h)
        }
        x?.right = deleteMax(x?.right)
        return balance(x)
    }

    // TODO
    fun delete(key: String) {

    }
}

fun main() {
    val bst = RedBlackTree()
    bst.put("3", 30)
    bst.put("1", 10)
    bst.put("2", 20)
    bst.put("2", 22)
    bst.put("5", 55)
    bst.put("7", 55)
    bst.preOrderPrintNodes()

    println()

    println("key: 2, get value: ${bst.get("2")}")
    println("min: ${bst.min()}")
    println("max: ${bst.max()}")

    println()

    bst.deleteMin()
    println("delete min")
    bst.preOrderPrintNodes()

    println()

    bst.deleteMax()
    println("delete max")
    bst.preOrderPrintNodes()

    println()

    bst.delete("3")
    println("delete node 3")
    bst.preOrderPrintNodes()

    println()

    bst.delete("7")
    println("delete node 7")
    bst.preOrderPrintNodes()
}