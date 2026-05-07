import java.io.BufferedWriter
import java.io.OutputStreamWriter

fun main() = with(System.`in`.bufferedReader()) {
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    while(true) {
        val input = readLine()
        if (input == null|| input.isBlank()) {
            break
        }

        val (preOrder, inOrder) = input.split(" ")
        bw.write("${getPostOrder(preOrder, inOrder, 0, preOrder.length, 0, inOrder.length)}\n")
    }
    bw.flush()
    bw.close()
}

fun getPostOrder(
    preOrder: String,
    inOrder: String,
    sPre: Int,
    ePre: Int,
    sIn: Int,
    eIn: Int
): String {
    if(sPre + 1 > ePre) return ""
    val root = preOrder[sPre]

    val (left, right) = getLeftAndRight(inOrder, root, sIn, eIn)
    return buildString {
        append(getPostOrder(preOrder, inOrder, sPre + 1, sPre + left + 1, sIn, sIn + left))
        append(getPostOrder(preOrder, inOrder, ePre - right, ePre, eIn - right, eIn))
        append(root.toString())
    }
}

fun getLeftAndRight(
    inOrder: String,
    root: Char,
    start: Int,
    end: Int,
): Pair<Int, Int> {
    for(i in start..< end) {
        if(inOrder[i] == root) return Pair(i - start, end - (i + 1))
    }
    return Pair(0, 0)
}