import kotlin.math.abs

fun main() = with(System.`in`.bufferedReader()) {
    val (startX, startY) = readLine()!!.split(' ').map { it.toInt() }
    val (targetX, targetY) = readLine()!!.split(' ').map { it.toInt() }

    val n = readLine()!!.toInt()
    val wind = readLine()!!

    val prefix = buildPrefix(wind)
    val dx = targetX - startX
    val dy = targetY - startY

    print(binarySearch(prefix, dx, dy, n))
}

fun buildPrefix(wind: String): Array<IntArray> {
    val n = wind.length
    val prefix = Array(n + 1) { IntArray(2) }

    for (i in 1..n) {
        when (wind[i - 1]) {
            'U' -> prefix[i][1]++
            'D' -> prefix[i][1]--
            'L' -> prefix[i][0]--
            'R' -> prefix[i][0]++
        }

        prefix[i][0] += prefix[i - 1][0]
        prefix[i][1] += prefix[i - 1][1]
    }

    return prefix
}

fun binarySearch(
    prefix: Array<IntArray>,
    dx: Int,
    dy: Int,
    n: Int
): Long {
    var answer = -1L
    var left = 0L
    var right = 2e14.toLong()

    while (left <= right) {
        val mid = (left + right) / 2

        if (canReach(prefix, mid, dx, dy, n)) {
            answer = mid
            right = mid - 1
        } else {
            left = mid + 1
        }
    }

    return answer
}

fun canReach(
    prefix: Array<IntArray>,
    days: Long,
    dx: Int,
    dy: Int,
    n: Int
): Boolean {
    val cycles = days / n
    val remain = (days % n).toInt()

    val windX = cycles * prefix[n][0] + prefix[remain][0]
    val windY = cycles * prefix[n][1] + prefix[remain][1]

    val distanceX = abs(dx - windX)
    val distanceY = abs(dy - windY)

    return distanceX + distanceY <= days
}
