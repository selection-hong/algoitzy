import java.util.*;

class Solution {

    int INF = 1_000_000_005;

    ArrayList<Integer>[] tree;
    int[][] dp;

    public int maximumPoints(int[][] edges, int[] coins, int k) {
        tree = new ArrayList[coins.length];
        dp = new int[coins.length][15];

        for(int i = 0; i < coins.length; i++) {
            tree[i] = new ArrayList<>();
            for(int j = 0; j < 15; j++) {
                dp[i][j] = INF;
            }
        }

        for(int i = 0; i < edges.length; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            tree[a].add(b);
            tree[b].add(a);
        }
        return dfs(coins, -1, 0, k, 0);
    }

    private int dfs(int[] coins, int p, int node, int k, int dev) {
        
        if(dev >= 14) dev = 14;
        if(dp[node][dev] < INF) return dp[node][dev];

        int res = -INF;
        int currentCoin = coins[node] >> dev;
        int val1 = currentCoin - k;
        int val2 = currentCoin >> 1;
        for(int i : tree[node]) {
            if(i != p) {
                val1 += dfs(coins, node, i, k, dev); 
                val2 += dfs(coins, node, i, k, dev + 1);
            }
        }
        return dp[node][dev] = Math.max(val1, val2);
    }
}