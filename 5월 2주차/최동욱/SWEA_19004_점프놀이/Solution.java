import java.io.*;
import java.util.*;

class Solution {

    // 배열 최대 사이즈
    final static int SIZE = 50;

    // 타일의 색깔별 좌표
    static ArrayList<int[]>[] colors = new ArrayList[SIZE * SIZE + 1];
    // List 초기화
    static {
        for(int i = 1; i <= SIZE * SIZE; i++) {
            colors[i] = new ArrayList<>();
        }
    }

    // 색상의 최소 거리 저장용
    static long[][] dp = new long[SIZE][SIZE];
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();

        StringBuilder sb = new StringBuilder();
        int T = readInt();
        for(int t = 1; t <= T; t++) {
            int n = readInt();
            int k = readInt();

            // 전역 변수 초기화 및 초기 세팅
            init(n, k);
            sb.append('#').append(t).append(' ').append(solve(n, k)).append('\n');
        }
        System.out.print(sb);
    }

    private static void init(int n, int k) throws IOException {
        // 색상 좌표 초기화
        for(int i = 1; i <= k; i++) {
            colors[i].clear();
        }

        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                // dp값 초기화
                dp[y][x] = -1;

                // 색상별 좌표 초기화
                int color = readInt();
                colors[color].add(new int[]{y, x});                
            }
        }
    }

    private static long solve(int n, int k) {
        // 색의 종류가 1 ~ k 중 하나라도 없다면 -1 반환
        for(int i = 1; i <= k; i++) {
            if(colors[i].isEmpty()) return -1;
        }

        long res = Long.MAX_VALUE;
        for(int[] cur : colors[1]) {
            res = Math.min(res, dfs(n, k, 2, cur[0], cur[1]));
        }
        return res;
    }

    private static long dfs(int n, int k, int idx, int cy, int cx) {
        if(idx == k + 1) return dp[cy][cx] = 0;
        long res = Long.MAX_VALUE;
        for(int[] next : colors[idx]) {
            int ny = next[0];
            int nx = next[1];

            long dist = getDistance(cy, cx, ny, nx);
            // 이전에 계산을 했다면, dp값 사용
            dist += (dp[ny][nx] == -1) ? dfs(n, k, idx + 1, ny, nx) : dp[ny][nx];
            if(res > dist) res = dist;
        }

        return dp[cy][cx] = res;
    }

    // 거리 구하기
    private static long getDistance(int cy, int cx, int ny, int nx) {
        return Math.abs(cy - ny) + Math.abs(cx - nx);
    }

    private static int readInt() throws IOException {
        while(c <= ' ') c = System.in.read();
        int n = 0;
        while(c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}