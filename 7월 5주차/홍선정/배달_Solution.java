package algoitzy.7월 5주차.홍선정;

import java.util.*;

class 배달_Solution {
    class Nd implements Comparable<Nd> {
        int v, w;
        Nd(int v, int w) { this.v = v; this.w = w; }
        public int compareTo(Nd o) { return this.w - o.w; }
    }

    public int solution(int n, int[][] r, int k) {
        List<List<Nd>> g = new ArrayList<>();
        for (int i = 0; i <= n; i++) g.add(new ArrayList<>());
        
        for (int[] e : r) {
            g.get(e[0]).add(new Nd(e[1], e[2]));
            g.get(e[1]).add(new Nd(e[0], e[2]));
        }
        
        int[] d = new int[n + 1];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[1] = 0;
        
        PriorityQueue<Nd> pq = new PriorityQueue<>();
        pq.offer(new Nd(1, 0));
        
        while (!pq.isEmpty()) {
            Nd c = pq.poll();
            
            if (d[c.v] < c.w) continue;
            
            for (Nd nx : g.get(c.v)) {
                int nw = c.w + nx.w;
                if (nw < d[nx.v]) {
                    d[nx.v] = nw;
                    pq.offer(new Nd(nx.v, nw));
                }
            }
        }
        int a = 0;
        for (int i = 1; i <= n; i++) {
            if (d[i] <= k) a++;
        }
        
        return a;
    }
}