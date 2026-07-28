import java.util.*;

class Solution {
    
    List<Integer>[] childs, edges;
    int[] deque, visited;
    
    public int solution(int n, int[][] lighthouse) {
        int answer = 0;
        
        childs = new ArrayList[n + 1];
        edges = new ArrayList[n + 1];
        deque = new int[n];
        visited = new int[n + 1];
        
        for(int i = 1; i <= n; i++) {
            childs[i] = new ArrayList<>();
            edges[i] = new ArrayList<>();
        }
        
        for(int[] i : lighthouse) {
            int v1 = i[0];
            int v2 = i[1];
            
            edges[v1].add(v2);
            edges[v2].add(v1);
        }
        
        bfs(n, 1);
        
        return solve(n, 1);
    }
    
    private void bfs(int n, int s) {
        int head = 0, tail = 0;
        deque[tail++] = s;
        visited[s] = 1;
        
        while(head < tail) {
            int p = deque[head++];
            
            for(int next : edges[p]) {
                if(visited[next] == 0) {
                    deque[tail++] = next;
                    childs[p].add(next);
                    visited[next] = 1;
                }
            }
        }
    }
    
    private int solve(int n, int s) {
        int[][] dp = new int[n + 1][2];
        int top = -1;
        deque[++top] = s;
        
        while(top > -1) {
            int cur = deque[top];
            if(visited[cur] == 2) {
                for(int next : childs[cur]) {
                    dp[cur][0] += dp[next][1];
                    dp[cur][1] += (dp[next][0] < dp[next][1]) ? dp[next][0] : dp[next][1];
                }
                top--;
            } else {
                visited[cur] = 2;
                dp[cur][0] = 0;
                dp[cur][1] = 1;
                for(int next : childs[cur]) {
                    deque[++top] = next;
                }
            }
        }
        
        return dp[s][0] < dp[s][1] ? dp[s][0] : dp[s][1];
    }
}