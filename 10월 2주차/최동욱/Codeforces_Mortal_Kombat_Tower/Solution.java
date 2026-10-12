import java.io.*;

class Main {

    final static int INF = 200_000;
    
    static int[][] dp = new int[INF][2];
    static int[] arr = new int[INF];
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int t = readInt();

        while(t-- > 0) {
            int n = readInt();

            for(int i = 0; i < n; i++) {
                dp[i][0] = dp[i][1] = -1;
                arr[i] = readInt();
            }

            sb.append(dfs(0, 1, n)).append('\n');
        }

        System.out.println(sb);
    }

    private static int dfs(int idx, int diff, int n) {
        if(idx >= n) return 0;
        if(dp[idx][diff] > -1) return dp[idx][diff];

        dp[idx][diff] = 0;

        int val = dfs(idx + 1, diff ^ 1, n) + arr[idx] * diff;
        if(idx + 1 < n) {
            int val2 = dfs(idx + 2, diff ^ 1, n) + (arr[idx] + arr[idx + 1]) * diff;
            val = Math.min(val, val2);
        }

        return dp[idx][diff] = val;
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