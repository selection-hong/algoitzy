import java.util.*;

class Solution {
    
    private class Node implements Comparable<Node> {
        int intensity, to, idx;
        
        public Node(int intensity, int to, int idx) {
            this.intensity = intensity;
            this.to = to;
            this.idx = idx;
        }
        
        @Override
        public int compareTo(Node n) {
            if(this.intensity != n.intensity) return this.intensity - n.intensity;
            else return this.idx - n.idx;
        }
    }
    
    private final int INF = 10_000_005;
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        List<int[]>[] list = new ArrayList[n + 1];
        int[] visited = new int[n + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        init(list, visited, n);
        inputPath(list, paths);
        markingGate(gates, visited);
        markingSummits(pq, summits, visited);
        
        return dijkstra(pq, list, visited);
    }
    
    private void init(List<int[]>[] list, int[] visited, int n) {
        for(int i = 0; i <= n; i++) {
            list[i] = new ArrayList<>();
            visited[i] = INF;
        }
    }
    
    private void inputPath(List<int[]>[] list, int[][] paths) {
        for(int[] path : paths) {
            int v1 = path[0];
            int v2 = path[1];
            int w = path[2];
            
            list[v1].add(new int[]{v2, w});
            list[v2].add(new int[]{v1, w});
        }        
    }
    
    private void markingGate(int[] gates, int[] visited) {
        for(int gate : gates) {
            visited[gate] = -2;
        }
    }
    
    private void markingSummits(
                PriorityQueue<Node> pq,
                int[] summits, 
                int[] visited
        ) {
        for(int summit : summits) {
            visited[summit] = 0;
            pq.add(new Node(0, summit, summit));
        }
    }
    
    private int[] dijkstra(
            PriorityQueue<Node> pq,
            List<int[]>[] list,
            int[] visited
            ) {
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            if(cur.to == -2) return new int[] {cur.idx, cur.intensity};
            
            int intensity = cur.intensity;
            int s = cur.to;
            int idx = cur.idx;
            
            if(visited[s] < intensity) continue;
            
            for(int[] edge : list[s]) {
                int next = edge[0];
                int w = edge[1] > intensity ? edge[1] : intensity;
                
                if(visited[next] == -2) {
                    pq.add(new Node(w, -2, idx));
                } else if(visited[next] > w) {
                    pq.add(new Node(w, next, idx));
                    visited[next] = w;
                }
            }
        }
        return new int[]{0, 0};
    }
}