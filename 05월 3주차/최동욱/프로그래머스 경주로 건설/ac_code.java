import java.util.*;

class Solution {
    
    // 좌표 정보와 해당 지점까지의 비용, 진행 방향을 저장하는 클래스
    private class Node implements Comparable<Node> {
        int y, x, cost, d;
        
        Node(int y, int x, int cost, int d) {
            this.y = y;
            this.x = x;
            this.cost = cost;
            this.d = d; // 0:우, 1:하, 2:좌, 3:상
        }
        
        // 우선순위 큐에서 비용을 기준으로 오름차순 정렬
        @Override
        public int compareTo(Node n) {
            return this.cost - n.cost;
        }
    }
    
    public int solution(int[][] board) {
        int n = board.length;
        // visited[y][x][direction]: 특정 좌표에 특정 방향으로 진입했을 때의 최소 비용 저장
        int[][][] visited = new int[n][n][4];
        // 이동 방향: 우, 하, 좌, 상
        int[][] dist = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        // 시작점(0,0)에서 오른쪽(0)과 아래쪽(1)으로 나가는 두 가지 경우를 초기값으로 설정
        pq.add(new Node(0, 0, 0, 0));
        pq.add(new Node(0, 0, 0, 1));
        
        int INF = Integer.MAX_VALUE;
        // 최소 비용 비교를 위해 모든 방문 배열을 무한대로 초기화
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < 4; k++) {
                    visited[i][j][k] = INF;
                }
            }
        }
        
        // 시작 지점의 비용은 0
        for(int i = 0; i < 4; i++) {
            visited[0][0][i] = 0;
        }
        
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            int y = cur.y;
            int x = cur.x;
            int cost = cur.cost;
            int d = cur.d;
            
            // 현재 꺼낸 비용이 이미 기록된 최소 비용보다 크면 스킵 (다익스트라 최적화)
            if(visited[y][x][d] < cost) continue;
            
            // 목적지에 도달하면 현재 비용 반환 (우선순위 큐이므로 가장 먼저 도달한 것이 최소 비용)
            if(y == n-1 && x == n-1) return cost;
            
            // 1. 직진하는 경우
            int ny = y + dist[d][0];
            int nx = x + dist[d][1];
            
            // 경계 내에 있고, 벽(1)이 아니며, 기존 기록보다 저렴하게 이동 가능할 때
            if(check(ny, nx, n) && board[ny][nx] == 0 && visited[ny][nx][d] > cost + 100) {
                pq.add(new Node(ny, nx, cost + 100, d));
                visited[ny][nx][d] = cost + 100;
            }
            
            // 2. 회전하는 경우 (현재 방향 d를 기준으로 왼쪽/오른쪽 90도 회전)
            // i=1 (시계방향 90도), i=3 (반시계방향 90도)
            for(int i = 1; i < 4; i += 2) {
                int nd = (d + i) & 3;
                ny = y + dist[nd][0];
                nx = x + dist[nd][1];
                
                // 회전 시: 코너 비용(500) + 직선 도로 비용(100) = 600 추가
                if(check(ny, nx, n) && board[ny][nx] == 0 && visited[ny][nx][nd] > cost + 600) {
                    pq.add(new Node(ny, nx, cost + 600, nd));
                    visited[ny][nx][nd] = cost + 600;
                }
            }
        }
        
        return -1; // 도달 불가능한 경우
    }
    
    // 좌표가 배열 범위 내에 있는지 확인하는 헬퍼 메소드
    private static boolean check(int y, int x, int n) {
        return y >= 0 && y < n && x >= 0 && x < n;
    }
}