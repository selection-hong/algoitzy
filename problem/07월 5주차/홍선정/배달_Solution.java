package algoitzy.7월 5주차.홍선정;

import java.util.*;

class 배달_Solution {
    /*
    알고리즘은 후보를 하나씩 제거하는 방식으로 선택.
    ex) dfs -> bfs -> 다익스트라 -> 플로이드 워셜 ...
    
    시작점인 1번 마을에서 i 번 마을까지 최단거리
    -> 다익스트라

    1번~~i번 마을까지 경우의 수를 따져볼 때 가중치의 합이 최소인 경로를 선택해야 함.
    dis[i] = 1번 마을에서 i번 마을까지의 최단거리 저장

    dis[i] 초기 설정 값 = Integer.MAX_VALUE
    dis[1] = 0 (1번 마을에서 1번 자기 자신까지의 최단거리는 0)

    그래프 저장 구조 결정 -> 목적 : 각 마을에서 연결된 도로 찾기
    -> 인접 리스트 구조로 저장
    




     */
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