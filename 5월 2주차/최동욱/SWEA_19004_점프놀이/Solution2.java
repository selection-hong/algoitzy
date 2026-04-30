import java.io.*;

class Solution {

    private static class Point {
        int y, x, pre;

        public Point() {}
    }

    final static int SIZE = 50;
    final static long INF = Long.MAX_VALUE;
    
    static Point[] point = new Point[SIZE * SIZE];
    static {
        for(int i = 0; i < SIZE * SIZE; i++) {
            point[i] = new Point();
        }
    }

    static long[] dp = new long[SIZE * SIZE];
    static int[] colors = new int[SIZE * SIZE + 1];
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int T = readInt();
        for(int t = 1; t <= T; t++) {
            int n = readInt();
            int k = readInt();
            init(n, k);
            inputArray(n, k);
            
            sb.append('#').append(t).append(' ').append(solve(n, k)).append('\n');
        }
        System.out.print(sb);
    }

    private static void init(int n, int k) {
        for(int i = 1; i <= k; i++) colors[i] = -1;
    }

    private static void inputArray(int n, int k) throws IOException {
        int idx = 0;
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                int color = readInt();
                Point p = point[idx];
                p.y = y;
                p.x = x;
                p.pre = colors[color];
                colors[color] = idx;

                dp[idx] = color < k ? INF : 0;
                idx++;
            }
        }
    }

    private static long solve(int n, int k) {
        for(int i = 1; i <= k; i++) {
            if(colors[i] == -1) return -1;
        }

        for(int i = k - 1; i > 0; i--) {
            int cur = colors[i];
            while(cur > -1) {
                Point p = point[cur];
                int cy = p.y;
                int cx = p.x;

                int next = colors[i + 1];
                while(next > -1) {
                    Point np = point[next];
                    int ny = np.y;
                    int nx = np.x;
                    long dist = dp[next] + getDistance(cy, cx, ny, nx);
                    if(dp[cur] > dist) dp[cur] = dist;
                    next = np.pre;
                }
                cur = p.pre;
            }
        }

        int cur = colors[1];
        long res = INF;
        while(cur > -1) {
            if(res > dp[cur]) res = dp[cur];
            cur = point[cur].pre;
        }
        return res;
    }

    private static int getDistance(int y, int x, int ny, int nx) {
        int dy = y > ny ? y - ny : ny - y;
        int dx = x > nx ? x - nx : nx - x;
        return dy + dx;
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