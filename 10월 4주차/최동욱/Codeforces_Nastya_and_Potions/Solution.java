import java.util.*;
import java.io.*;

class Main {

    static final int INF = 200_000;
    
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int t = readInt();
        while(t-- > 0) {
            int n = readInt();
            int k = readInt();
            
            List<Integer>[] parents = new ArrayList[n + 1];            
            int[] cnt = new int[n + 1];
            long[] cost = new long[n + 1];
            for(int i = 1; i <= n; i++) {
                parents[i] = new ArrayList<>();
                cost[i] = readInt();
            }
            
            for(int i = 0; i < k; i++) {
                int idx = readInt();
                cost[idx] = 0;
            }

            for(int i = 1; i <= n; i++) {
                int len = readInt();
                cnt[i] = len;
                while(len-- > 0) {
                    int val = readInt();
                    parents[val].add(i);
                }
            }

            topologicalSort(parents, cnt, cost, n);

            for(int i = 1; i <= n; i++) {
                sb.append(cost[i]).append(' ');
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }

    private static void topologicalSort(List<Integer>[] parents, int[] cnt, long[] cost, int n) {
        long[] dp = new long[n + 1];
        int[] que = new int[n + 1];
        int head = 0, tail = 0;
        
        for(int i = 1; i <= n; i++) {
            if(cnt[i] == 0) {
                que[tail++] = i;
            }
        }

        while(head < tail) {
            int cur = que[head++];

            for(int child : parents[cur]) {
                cnt[child]--;
                dp[child] += cost[cur];

                if(cnt[child] == 0) {
                    cost[child] = Math.min(cost[child], dp[child]);
                    que[tail++] = child;
                }
            }
        }
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