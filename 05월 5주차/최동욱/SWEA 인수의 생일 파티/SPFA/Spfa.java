package SPFA;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * SPFA (Shortest Path Faster Algorithm) 구현 클래스
 * - 최단 경로 탐색을 위해 PriorityQueue 대신 ArrayDeque를 활용하여 오버헤드 최소화
 * - 그래프 전파(Propagation) 특성이 강한 문제에서 다익스트라보다 우수한 퍼포먼스를 보임
 */
public class Spfa {
    List<int[]>[] edge; // 인접 리스트: [연결노드, 가중치]
    int[] visited;           // 최단 거리 저장 배열 (dist)

    public Spfa(int size) {
        edge = new ArrayList[size + 1];
        visited = new int[size + 1];

        for (int i = 0; i <= size; i++) {
            edge[i] = new ArrayList<>();
        }
    }

    /**
     * 테스트 케이스별 데이터 초기화
     * @param n 현재 TC의 노드 개수
     * @param val 초기 거리값 (INF)
     */
    public void init(int n, int val) {
        for (int i = 0; i <= n; i++) {
            edge[i].clear();
            visited[i] = val;
        }
    }

    /**
     * 단방향 간선 정보 입력
     */
    public void inputEdge(int from, int to, int w) {
        edge[from].add(new int[]{to, w});
    }

    /**
     * SPFA 탐색 로직
     * @param n 전체 노드 수
     * @param idx 시작 노드 번호
     */
    public void search(int n, int idx) {
        // 1. ArrayDeque를 활용한 큐 기반 전파 (정렬 비용 O(log E) 제거)
        Queue<int[]> que = new ArrayDeque<>();
        que.add(new int[]{idx, 0});
        visited[idx] = 0;
        
        while (!que.isEmpty()) {
            int[] node = que.poll();    
            int cur = node[0];
            int w = node[1];

            // 2. [최적화] 현재 기록된 거리보다 큰 경로는 탐색 제외 (Dijkstra의 continue 로직과 유사)
            if (visited[cur] < w) continue;

            // 3. 인접 노드 탐색 및 거리 갱신 (Relaxation)
            for (int[] next : edge[cur]) {
                int to = next[0];
                int nextW = w + next[1];
                
                // 더 짧은 경로가 발견될 경우에만 큐에 삽입 (정보의 전파)
                if (visited[to] > nextW) {
                    que.add(new int[]{to, nextW});
                    visited[to] = nextW;
                }
            }
        }
    }

    /**
     * 탐색 완료된 최단 거리 배열 반환
     */
    public int[] getVisited() {
        return visited;
    }
}