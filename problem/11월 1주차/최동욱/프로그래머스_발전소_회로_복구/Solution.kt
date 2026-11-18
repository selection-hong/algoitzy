import java.util.*
import kotlin.math.abs

class Solution {

    data class Node(var y: Int, var x: Int, var time: Int)

    lateinit var grid: Array<String>
    lateinit var panels: Array<IntArray>

    lateinit var dp: Array<IntArray>
    lateinit var visited: Array<IntArray>

    lateinit var memo: Array<IntArray>
    lateinit var order: IntArray;

    val dist = arrayOf(
        intArrayOf(0, 1),
        intArrayOf(0, -1),
        intArrayOf(1, 0),
        intArrayOf(-1, 0)
    )

    val inf = 1_000_000_000

    var res: Int = inf
    var liftY: Int = 0
    var liftX: Int = 0
    var r: Int = 0
    var c: Int = 0
    var mark: Int = 0

    fun solution(h: Int, grid: Array<String>, panels: Array<IntArray>, seqs: Array<IntArray>): Int {
        this.grid = grid
        this.panels = panels

        for(panel in panels) {
            panel[1]--
            panel[2]--
        }

        r = grid.size
        c = grid[0].length

        findLiftLocation(r, c).let { (y, x) ->
            liftY = y
            liftX = x
        }

        val n = panels.size
        dp = Array(n + 1) {IntArray(n + 1) { -1 } }
        visited = Array(r) { IntArray(c) { 0 } }

        memo = Array(1 shl (n + 1)) { IntArray(n + 1) { inf } }
        order = IntArray(n + 1)

        for(seq in seqs) {
            val (u, v) = seq
            order[v] = order[v] or (1 shl u)
        }

        solve(1, 0, 0, n)

        return res
    }

    fun findLiftLocation(n: Int, m: Int): Pair<Int, Int> {
        for(y in 0..<n) {
            for(x in 0..<m) {
                if(grid[y][x] == '@') {
                    return Pair(y, x)
                }
            }
        }

        return Pair(-1, -1)
    }

    fun solve(idx: Int, time: Int, bit: Int, n: Int) {
        if(bit == (1 shl (n + 1)) - 2) {
            if(res > time) {
                res = time
            }
            return
        }

        if(res <= time) return

        if(memo[bit][idx] <= time) return
        memo[bit][idx] = time

        for(next in 1..n) {
            if((bit and (1 shl next)) == 0 && ((bit and order[next])) == order[next]) {
                val nTime = getTime(idx, next)
                solve(next, time + nTime, bit or (1 shl next), n)
            }
        }
    }

    fun getTime(idx1: Int, idx2: Int): Int {
        if(dp[idx1][idx2] > -1) return dp[idx1][idx2]

        var time = 0
        if(idx2 == 0 || panels[idx1 - 1][0] == panels[idx2 - 1][0]){
            time = bfs(idx1, idx2)
        } else {
            val time1 = getTime(idx1, 0)
            val time2 = getTime(idx2, 0)
            time = time1 + time2 + abs(panels[idx1 - 1][0] - panels[idx2 - 1][0])
        }

        dp[idx1][idx2] = time
        dp[idx2][idx1] = time
        return time
    }

    fun bfs(idx1: Int, idx2: Int): Int {
        var ey = 0
        var ex = 0
        if(idx2 == 0) {
            ey = liftY
            ex = liftX
        } else {
            ey = panels[idx2 - 1][1]
            ex = panels[idx2 - 1][2]
        }

        val que: Queue<Node> = ArrayDeque()
        que.add(Node(panels[idx1 - 1][1], panels[idx1 - 1][2], 0))

        visited[panels[idx1 - 1][1]][panels[idx1 - 1][2]] = ++mark

        while(que.isNotEmpty()) {
            val cur: Node = que.remove()

            if(cur.y == ey && cur.x == ex) {
                return cur.time
            }

            for(d in dist) {
                val ny = d[0] + cur.y
                val nx = d[1] + cur.x

                if(check(ny, nx) && grid[ny][nx] != '#' && visited[ny][nx] < mark) {
                    visited[ny][nx] = mark
                    que.add(Node(ny, nx, cur.time + 1))
                }
            }
        }

        return -1
    }

    fun check(y: Int, x: Int): Boolean =
        y in 0..<r && x in 0..<c
}