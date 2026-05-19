import Dijkstra.Dijkstra;
import Input.Input;
import java.io.IOException;

class Solution {    

    // 문제 제약 사항에 따른 상수 설정
    static final int MAX_SIZE = 1000;
    static final int INF = 1_000_000;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        Input input = new Input();
        int T = input.readInt();

        // GC 부하를 줄이기 위해 루프 밖에서 인스턴스 생성 및 재사용
        Dijkstra dij1 = new Dijkstra(MAX_SIZE); // 정방향: S -> Every
        Dijkstra dij2 = new Dijkstra(MAX_SIZE); // 역방향: Every -> S (돌아오는 길)

        for(int t = 1; t <= T; t++) {
            int n = input.readInt();
            int m = input.readInt();
            int s = input.readInt();

            dij1.init(n, INF);
            dij2.init(n, INF);

            while(m-- > 0) {
                int v1 = input.readInt();
                int v2 = input.readInt();
                int w = input.readInt();

                // 정방향 그래프 구성
                dij1.inputEdge(v1, v2, w);
                // 모든 노드에서 S로 오는 최단 거리는 '간선을 뒤집은 그래프'에서 S의 다익스트라와 동일
                dij2.inputEdge(v2, v1, w);
            }

            // 각각 다익스트라 수행
            dij1.search(n, s);
            dij2.search(n, s);

            int[] dist1 = dij1.getVisited();
            int[] dist2 = dij2.getVisited();

            // 결과 계산 및 출력 형식 저장
            sb.append('#').append(t).append(' ').append(solve(dist1, dist2, n)).append('\n');
        }   

        System.out.print(sb);
    }

    /**
     * 왕복 거리(가는 길 + 오는 길) 중 최대값을 찾는 로직
     */
    private static int solve(int[] dist1, int[] dist2, int n) {
        int result = 0;

        for(int i = 1; i <= n; i++) {
            int val = dist1[i] + dist2[i];
            if(result < val) result = val;
        }
        return result;
    }
}