import java.util.*;

class Solution {
    
    // 좌표, 누적 비용, 현재 방향을 관리하는 노드 클래스
    private class Node {
        int y, x, cost, d;
        
        Node(int y, int x, int cost, int d) {
            this.y = y;
            this.x = x;
            this.cost = cost;
            this.d = d;
        }
    }
    
    public int solution(int[][] board) {
        int n = board.length;
        // visited[y][x][direction]: 해당 좌표에 특정 방향으로 도달했을 때의 최소 비용
        int[][][] visited = new int[n][n][4];
        // 0:우, 1:하, 2:좌, 3:상 (시계 방향)
        int[][] dist = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        // 0-1 BFS와 유사하게 가중치 차이에 따른 탐색 우선순위 조절을 위해 Deque 사용
        Deque<Node> deque = new ArrayDeque<>();
        // 시작점(0,0)에서 오른쪽(0) 또는 아래(1)로 출발하는 초기 상태 삽입
        deque.addLast(new Node(0, 0, 0, 0));
        deque.addLast(new Node(0, 0, 0, 1));

        int INF = Integer.MAX_VALUE;
        // 최소 비용 비교를 위해 전체 배열을 INF로 초기화
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < 4; k++) {
                    visited[i][j][k] = INF;
                }
            }
        }

        // 출발 지점의 모든 방향 진입 비용을 0으로 초기화
        for(int i = 0; i < 4; i++) {
            visited[0][0][i] = 0;
        }

        while(!deque.isEmpty()) {
            Node cur = deque.pollFirst();
            int y = cur.y;
            int x = cur.x;
            int cost = cur.cost;
            int d = cur.d;

            // 다익스트라 최적화: 현재 뽑은 경로가 이미 기록된 경로보다 비싸면 탐색 제외
            if(visited[y][x][d] < cost) continue;
            // 목적지에 도달한 경우라도 다른 방향 진입 최적화를 위해 루프는 계속 진행
            if(y == n - 1 && x == n - 1) continue;

            // 1. 현재 방향 그대로 직진 (비용 100 추가)
            int ny = y + dist[d][0];
            int nx = x + dist[d][1];
            
            // 경계 내, 빈 칸(0), 그리고 더 적은 비용으로 이동 가능할 때
            if(check(ny, nx, n) && board[ny][nx] == 0 && visited[ny][nx][d] > cost + 100) {
                visited[ny][nx][d] = cost + 100;
                // 가중치가 낮은 직진 케이스를 덱의 앞(First)에 넣어 우선 탐색 유도
                deque.addFirst(new Node(ny, nx, cost + 100, d));
            }

            // 2. 방향 전환 (현재 d 기준 시계/반시계 90도 회전)
            for(int i = 1; i < 4; i += 2) {
                // 비트 연산(& 3)을 통한 나머지 연산 최적화 적용
                int nd = (d + i) & 3; 
                ny = y + dist[nd][0];
                nx = x + dist[nd][1];

                // 회전 시: 코너 설치(500) + 직선 도로(100) = 600 추가
                if(check(ny, nx, n) && board[ny][nx] == 0 && visited[ny][nx][nd] > cost + 600) {
                    visited[ny][nx][nd] = cost + 600;
                    // 가중치가 높은 회전 케이스는 덱의 뒤(Last)에 삽입
                    deque.addLast(new Node(ny, nx, cost + 600, nd));
                }
            }
        }
        
        // 최종 목적지 (n-1, n-1)에 도달한 각 방향별 최소값 중 최종 결과 산출
        int res = visited[n-1][n-1][0] > visited[n-1][n-1][1] ? visited[n-1][n-1][1] : visited[n-1][n-1][0];
        return res;
    }
    
    // 좌표의 유효 범위를 체크하는 헬퍼 함수
    private static boolean check(int y, int x, int n) {
        return y >= 0 && y < n && x >= 0 && x < n;
    }
}