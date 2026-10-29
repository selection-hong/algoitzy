fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = readLine()!!.split(' ').map { it.toInt() }

    val arr = Array(n) {
        readLine()!!.split(' ').map { it.toInt() }.toIntArray()
    }

    val man = Array(n) { Array(m) { IntArray(4) } }
    val woman = Array(n) { Array(m) { IntArray(4) } }

    val dist = arrayOf(
        intArrayOf(1, 0),   // 아래
        intArrayOf(-1, 0),  // 위
        intArrayOf(0, 1),   // 오른쪽
        intArrayOf(0, -1)  // 왼쪽
    )

    settingDp(arr, man, dist, 0, 2)
    settingDp(arr, man, dist, 1, 3)
    settingDp(arr, woman, dist, 1, 2)
    settingDp(arr, woman, dist, 0, 3)

    print(solve(man, woman, n, m))
}

fun settingDp(
    array: Array<IntArray>,
    dp: Array<Array<IntArray>>,
    dist: Array<IntArray>,
    d1: Int,
    d2: Int
) {
    val n = array.size
    val m = array[0].size

    val ys = if (dist[d1][0] == 1 || dist[d2][0] == 1) n - 1 downTo 0 else 0..<n
    val xs = if (dist[d1][1] == 1 || dist[d2][1] == 1) m - 1 downTo 0 else 0..<m

    for (y in ys) {
        for (x in xs) {
            for (d in intArrayOf(d1, d2)) {
                val ny = y + dist[d][0]
                val nx = x + dist[d][1]

                if (!check(ny, nx, n, m)) continue

                dp[y][x][d] = array[ny][nx] + maxOf(dp[ny][nx][d1], dp[ny][nx][d2])
            }
        }
    }
}

fun check(y: Int, x: Int, n: Int, m: Int): Boolean =
    y in 0..<n && x in 0..<m

fun solve(
    man: Array<Array<IntArray>>,
    woman: Array<Array<IntArray>>,
    n: Int,
    m: Int
): Int {
    var res = 0

    for (y in 1..<n - 1) {
        for (x in 1..<m - 1) {
            val case1 = man[y][x][0] + man[y][x][1] + woman[y][x][2] + woman[y][x][3]
            val case2 = man[y][x][2] + man[y][x][3] + woman[y][x][0] + woman[y][x][1]
            res = maxOf(res, maxOf(case1, case2))
        }
    }

    return res
}
