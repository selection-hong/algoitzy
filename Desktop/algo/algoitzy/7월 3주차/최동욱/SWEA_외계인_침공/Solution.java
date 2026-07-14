import java.io.*;

class Solution {

    static final int MAX_SIZE = 1_000_000;
    
    static long[][] dp = new long[MAX_SIZE][2];
    static int[] arr = new int[MAX_SIZE];
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();
        int T = readInt();

        for(int t = 1; t <= T; t++) {
            int n = readInt();

            for(int i = 0; i < n; i++) {
                arr[i] = readInt();
            }

            dp[0][0] = arr[0];

            for(int i = 1; i < n; i++) {
                dp[i][0] = dp[i - 1][1] + arr[i];
                dp[i][1] = dp[i - 1][0] > dp[i - 1][1] ? dp[i - 1][0] : dp[i - 1][1];
            }

            long res = dp[n-1][0] > dp[n-1][1] ? dp[n-1][0] : dp[n-1][1];
            sb.append('#').append(t).append(' ').append(res).append('\n');
        }
        System.out.print(sb);
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