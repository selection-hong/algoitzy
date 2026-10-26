data class Node(val child: Int, val remove: Int)

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    repeat(readLine()!!.toInt()) {
        val n = readLine()!!.toInt()
        val graph = Array(n + 1) { mutableListOf<Int>() }

        repeat(n - 1) {
            val (u, v) = readLine()!!.split(" ").map { it.toInt() }
            graph[u].add(v)
            graph[v].add(u)
        }

        sb.append(dfs(graph, -1, 1).remove).append('\n')
    }

    print(sb)
}

fun dfs(graph: Array<MutableList<Int>>, parent: Int, node: Int): Node {
    val children = graph[node].mapNotNull {
        if (it == parent) null else dfs(graph, node, it)
    }

    return when (children.size) {
        0 -> Node(0, 0)
        1 -> children[0].let { Node(it.child + 1, it.child) }
        else -> {
            val (a, b) = children
            Node(
                a.child + b.child + 2,
                maxOf(a.child + b.remove, a.remove + b.child)
            )
        }
    }
}