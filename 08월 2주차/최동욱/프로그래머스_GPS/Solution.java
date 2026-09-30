import java.util.*;

class Solution {
    public int solution(int n, int m, int[][] edge_list, int k, int[] gps_log) {
        int[][] edges = new int[n + 2][n + 2];
        
        for(int i = 1; i <= n; i++) {
            edges[i][0] = 1;
            edges[i][1] = i;
        }

        for(int i = 0; i < m; i++) {
            int e1 = edge_list[i][0];
            int e2 = edge_list[i][1];

            edges[e1][++edges[e1][0]] = e2;
            edges[e2][++edges[e2][0]] = e1;
        }

        Deque<int[]> deque = new ArrayDeque<>();
        deque.addLast(new int[]{gps_log[0], 0, 0});

        int[][] dist = new int[n + 1][k];

        for(int i = 0; i <= n; i++) {
            for(int j = 0; j < k; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        dist[gps_log[0]][0] = 0;

        while(!deque.isEmpty()) {
            int[] temp = deque.pollFirst();
            int e = temp[0];
            int t = temp[1];
            int cnt = temp[2];

            if(dist[e][t] < cnt) continue;

            if(t == k - 1) {
                if(e == gps_log[t]) return cnt;
                else continue;
            }

            for(int i = 1; i <= edges[e][0]; i++) {
                int next = edges[e][i];

                if(gps_log[t + 1] == next) {
                    if(dist[next][t + 1] > cnt) {
                        dist[next][t + 1] = cnt;
                        deque.addFirst(new int[]{next, t + 1, cnt});
                    }
                } else {
                    if(dist[next][t + 1] > cnt + 1) {
                        dist[next][t + 1] = cnt + 1;
                        deque.addLast(new int[]{next, t + 1, cnt + 1});
                    }
                }
            }
        }

        return -1;
    }
}