import java.util.*;

class Solution {
    public boolean solution(int n, int[][] path, int[][] order) {
        List<Integer>[] list = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
        }
        
        for(int[] p : path) {
            int from = p[0];
            int to = p[1];
            
            list[from].add(to);
            list[to].add(from);
        }
        
        int[] key = new int[n];
        for(int[] o : order) {
            int front = o[0];
            int next = o[1];
            
            key[front] = next;
            key[next] = -front;
        }
        
        return bfs(list, key, n, 0);
    }
    
    private boolean bfs(List<Integer>[] list, int[] key, int n, int start) {
        if(key[start] < 0) return false;
        
        int[] que = new int[n];
        int head = 0, tail = 0;
        que[tail++] = start;
        
        boolean[] visited = new boolean[n];
        visited[start] = true;
        Set<Integer> set = new HashSet<>();
        
        while(head < tail) {
            int cur = que[head++];
            
            if(key[cur] > 0) {
                int val = key[cur];
                if(set.contains(val)) {
                    que[tail++] = val;
                    visited[val] = true;
                } else {
                    key[val] = 0;
                }
            }
            
            for(int next : list[cur]) {
                if(visited[next]) continue;
                
                if(key[next] < 0) {
                    set.add(next);
                } else {
                    que[tail++] = next;
                    visited[next] = true;
                }
            }
        }
        
        return tail == n;
    }
}