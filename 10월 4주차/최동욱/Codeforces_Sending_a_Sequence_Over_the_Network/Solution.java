import java.io.*;

class Main {

    static final int INF = 200_000;

    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int[] arr = new int[INF];

        int t = readInt();
        while(t-- > 0) {
            int n = readInt();
            for(int i = 0; i < n; i++) {
                arr[i] = readInt();
            }

            boolean res = solve(arr, n);
            sb.append(res ? "YES" : "NO").append('\n');
        }
        System.out.println(sb);
    }

    private static boolean solve(int[] arr, int n) {
        if(n == 1) return false;
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for(int i = 0; i < n; i++) {
            if(dp[i] && arr[i] < n - i) {
                dp[i + arr[i] + 1] = true;
            }

            if(arr[i] <= i && dp[i - arr[i]]) {
                dp[i + 1] = true;
            }
        }
        
        return dp[n];
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