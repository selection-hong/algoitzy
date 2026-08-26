import java.util.*;

class Solution {

    PriorityQueue<int[]> pq;
    int[][] visited;

    public int solution(int n, int start, int end, int[][] roads, int[] traps) {
        List<int[]>[] forward = new ArrayList[n + 1];
        List<int[]>[] backward = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            forward[i] = new ArrayList<>();
            backward[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            int p = road[0];
            int q = road[1];
            int time = road[2];

            forward[p].add(new int[]{q, time});
            backward[q].add(new int[]{p, time});
        }

        // int[3] = {정점, 걸린 시간, 활성화된 함정 상태(bit)}
        pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.add(new int[]{start, 0, 0});

        // visited[node][mark] = 해당 상태까지의 최소 시간
        visited = new int[n + 1][1 << traps.length];

        for (int i = 1; i <= n; i++) {
            Arrays.fill(visited[i], Integer.MAX_VALUE);
        }

        visited[start][0] = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();

            int p = cur[0];
            int time = cur[1];
            int mark = cur[2];

            if (p == end) {
                return time;
            }

            if (visited[p][mark] < time) {
                continue;
            }

            boolean pActive = isActive(p, mark, traps);

            // 원래 정방향 간선
            dijkstra(
                forward,
                p,
                time,
                mark,
                pActive,
                traps,
                false
            );

            // 원래 역방향 간선
            dijkstra(
                backward,
                p,
                time,
                mark,
                pActive,
                traps,
                true
            );
        }

        return -1;
    }

    private void dijkstra(
        List<int[]>[] list,
        int p,
        int time,
        int mark,
        boolean pActive,
        int[] traps,
        boolean reverse
    ) {
        for (int[] next : list[p]) {
            int q = next[0];
            int nTime = time + next[1];

            int trapIndex = getTrapIndex(q, traps);
            boolean qActive = isActive(q, mark, traps);

            // 두 정점 중 하나만 활성화되어 있으면 간선 방향이 뒤집힌다.
            boolean reversed = pActive ^ qActive;

            // 현재 보고 있는 간선 방향과 실제 방향이 다르면 이동 불가
            if (reversed != reverse) {
                continue;
            }

            int nMark = mark;

            // 함정에 도착하면 활성 상태 토글
            if (trapIndex >= 0) {
                nMark ^= (1 << trapIndex);
            }

            if (visited[q][nMark] <= nTime) {
                continue;
            }

            visited[q][nMark] = nTime;
            pq.add(new int[]{q, nTime, nMark});
        }
    }

    private boolean isActive(int node, int mark, int[] traps) {
        int trapIndex = getTrapIndex(node, traps);

        if (trapIndex < 0) {
            return false;
        }

        return (mark & (1 << trapIndex)) != 0;
    }

    private int getTrapIndex(int node, int[] traps) {
        for (int i = 0; i < traps.length; i++) {
            if (node == traps[i]) {
                return i;
            }
        }

        return -1;
    }
}