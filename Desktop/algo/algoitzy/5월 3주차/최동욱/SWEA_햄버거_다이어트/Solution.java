import java.io.*;

class Solution {

    static final int MAX_RANGE = 10_000;
    static final int MAX_SIZE = 20;
    
    static int[] dp = new int[MAX_RANGE + 1];
    static int c;   
    
	public static void main(String args[]) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();
        int T = readInt();
        
        for(int t = 1; t <= T; t++) {
            int n = readInt();
            int kcal = readInt();

            init(kcal);            
            sb.append('#').append(t).append(' ').append(solve(n, kcal)).append('\n');
        }
        System.out.print(sb);
	}

    private static void init(int kcal) {
        for(int i = 0; i <= kcal; i++) dp[i] = 0;
    }
    
    private static int solve(int n, int kcal) throws IOException {
        for(int i = 0; i < n; i++) {
            int score = readInt();
            int k = readInt();
            for(int j = kcal; j >= k; j--) {
                dp[j] = Math.max(dp[j - k] + score, dp[j]);
            }
        }
        
        return dp[kcal];
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