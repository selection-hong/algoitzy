fun main() = with(System.`in`.bufferedReader()) {
    val input = readLine().split(" ")
    val n = input[0].toInt()
    val m = input[1].toLong()
    val arr = readLine().split(" ").map {it.toInt()}.toIntArray()

    var cnt: Int = 0
    var cost: Long = 0

    var left: Int = 0
    var right: Int = n

    while(left <= right) {
        val mid = (left + right) / 2

        val (flag, total) = search(arr, mid, m)

        if(flag) {
            cnt = mid
            cost = total
            left = mid + 1
        } else {
            right = mid - 1
        }
    }

    print("$cnt $cost")
}

fun search(arr: IntArray, target: Int, total: Long): Pair<Boolean, Long> {
    val temp = LongArray(arr.size) { i ->
        arr[i] + (i + 1L) * target
    }
    temp.sort()

    var cost = 0L
    for(i in 0..<target) {
        cost += temp[i]
        if(cost > total) return Pair(false, -1L)
    }

    return Pair(true, cost)
}