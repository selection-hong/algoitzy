import java.io.*;

class Main {

    final static int INF = 200_000;

    static int[] arr = new int[INF];
    static int[] dp = new int[INF];
    
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int t = readInt();
        while(t-- > 0) {
            int n = readInt();

            for(int i = 0; i < n; i++) {
                arr[i] = readInt();
                dp[i] = -1;
            }

            sb.append(dfs(0, n)).append('\n');
        }

        System.out.println(sb);
    }

    private static int dfs(int node, int n) {
        if(node >= n) return 0;

        if(dp[node] > -1) return dp[node];
            
        int val = dfs(node + 1, n) + 1;
        int next = node + arr[node] + 1;
        if(next <= n) {
            int temp = dfs(next, n);
            if(val > temp) val = temp;
        }
        
        return dp[node] = val;
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