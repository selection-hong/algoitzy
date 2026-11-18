fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    val t = readLine()!!.toInt()

    repeat(t) {
        val n = readLine()!!.toInt()
        val arr = readLine()!!.split(" ").map { it.toInt() }

        var level = 0
        var start = 0
        var end = 1

        while(end < n) {
            val len = end - start
            var cnt = 0
            start = end
            level++

            while(end < n) {
                if(end > start && arr[end - 1] > arr[end]) {
                    cnt++
                    if(cnt == len) break
                }
                end++
            }
        }

        sb.append("$level\n")
    }

    println(sb.toString())
}