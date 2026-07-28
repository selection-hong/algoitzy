package Dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 다익스트라 알고리즘 모듈
 * 인스턴스화를 통해 정방향/역방향 탐색 상태를 독립적으로 관리
 */
public class Dijkstra {
    List<int[]>[] edge; // 인접 리스트: [연결노드, 가중치]
    int[] visited;      // 최단 거리 저장 배열

    public Dijkstra(int size) {
        edge = new ArrayList[size + 1];
        visited = new int[size + 1];

        for(int i = 0; i <= size; i++) {
            edge[i] = new ArrayList<>();
        }
    }

    /**
     * 테스트 케이스마다 객체를 재사용하기 위한 초기화 메서드
     */
    public void init(int n, int val) {
        for(int i = 0; i <= n; i++) {
            edge[i].clear();
            visited[i] = val;
        }
    }

    public void inputEdge(int from, int to, int w) {
        edge[from].add(new int[]{to, w});
    }

    /**
     * 다익스트라 핵심 탐색 로직
     * @param n 전체 노드 수 (조기 종료 조건용)
     * @param idx 시작 노드 번호
     */
    public void search(int n, int idx) {
        int total = 1; // 확정된 최단 경로 노드 수
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        
        pq.add(new int[]{idx, 0});
        visited[idx] = 0;
        
        // 모든 노드의 최단 거리가 확정되면 더 이상 탐색하지 않음 (total < n)
        while(!pq.isEmpty() && total < n) {
            int[] node = pq.poll();    
            int cur = node[0];
            int w = node[1];

            // 이미 더 짧은 경로로 방문했다면 스킵
            if(visited[cur] < w) continue;
            total++; // 최단 거리 확정 노드 카운트 증가

            for(int[] next : edge[cur]) {
                int to = next[0];
                int nextW = w + next[1];
                
                // 릴랙세이션 (Relaxation): 더 짧은 경로 발견 시 갱신 및 PQ 삽입
                if(visited[to] > nextW) {
                    pq.add(new int[]{to, nextW});
                    visited[to] = nextW;
                }
            }
        }
    }

    public int[] getVisited() {
        return visited;
    }
}
