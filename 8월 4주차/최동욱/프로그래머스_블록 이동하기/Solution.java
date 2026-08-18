import java.util.*;

class Solution {
    
    static Queue<int[]> que = new ArrayDeque<>();
    static boolean[][][] visited; // (0, 1, 2, 3) => (우, 하, 좌, 상)
    static int n;
    
    public int solution(int[][] board) {
        int answer = -1;
        n = board.length;
        
        que.add(new int[]{0, 0, 0, 0}); // {y, x, dir, time}, dir: 0 가로, dir: 1 세로
            
        visited = new boolean[n][n][4];
        visited[0][0][0] = true;
        visited[0][1][2] = true;
        
        while(!que.isEmpty()) {
            int[] cur = que.poll();
            int cy1 = cur[0];
            int cx1 = cur[1];
            int d = cur[2];
            int time = cur[3];
            
            // 기준 좌표와 방향을 이용해 로봇의 두 번째 칸 계산
            int cy2 = cy1 + d;
            int cx2 = cx1 + (d ^ 1);
            
            if((cy1 == n - 1 && cx1 == n - 1) 
               || (cy2 == n - 1 && cx2 == n - 1)) {
                return time;
            }
            
            // 가로 상태
            if(d == 0) {

                // 좌측 평행 이동
                if(cx1 - 1 >= 0 && board[cy1][cx1 - 1] == 0) {
                    move(cy1, cx1 - 1, cy1, cx1, time, 0);
                }
                
                // 우측 평행 이동
                if(cx2 + 1 < n && board[cy2][cx2 + 1] == 0) {
                    move(cy2, cx2, cy2, cx2 + 1, time, 0);
                }
                
                // 아래쪽 두 칸이 비어 있으면 아래 이동 및 양쪽 축 회전 가능
                if(cy1 + 1 < n && cy2 + 1 < n
                  && board[cy1 + 1][cx1] == 0 && board[cy2 + 1][cx2] == 0) {

                    // 아래 평행 이동
                    move(cy1 + 1, cx1, cy2 + 1, cx2, time, 0);

                    // 왼쪽 칸을 축으로 시계 방향 회전
                    move(cy1, cx1, cy1 + 1, cx1, time, 1);

                    // 오른쪽 칸을 축으로 반시계 방향 회전
                    move(cy2, cx2, cy2 + 1, cx2, time, 1);
                }
                
                // 위쪽 두 칸이 비어 있으면 위 이동 및 양쪽 축 회전 가능
                if(cy1 - 1 >= 0 && cy2 - 1 >= 0
                  && board[cy1 - 1][cx1] == 0 && board[cy2 - 1][cx2] == 0) {

                    // 위 평행 이동
                    move(cy1 - 1, cx1, cy2 - 1, cx2, time, 0);

                    // 왼쪽 칸을 축으로 반시계 방향 회전
                    move(cy1 - 1, cx1, cy1, cx1, time, 1);

                    // 오른쪽 칸을 축으로 시계 방향 회전
                    move(cy2 - 1, cx2, cy2, cx2, time, 1);
                }

            // 세로 상태
            } else {

                // 위쪽 평행 이동
                if(cy1 - 1 >= 0 && board[cy1 - 1][cx1] == 0) {
                    move(cy1 - 1, cx1, cy1, cx1, time, 1);
                }
                
                // 아래쪽 평행 이동
                if(cy2 + 1 < n && board[cy2 + 1][cx2] == 0) {
                    move(cy2, cx2, cy2 + 1, cx2, time, 1);
                }
                
                // 오른쪽 두 칸이 비어 있으면 우측 이동 및 양쪽 축 회전 가능
                if(cx1 + 1 < n && cx2 + 1 < n
                  && board[cy1][cx1 + 1] == 0 && board[cy2][cx2 + 1] == 0) {

                    // 오른쪽 평행 이동
                    move(cy1, cx1 + 1, cy2, cx2 + 1, time, 1);

                    // 위쪽 칸을 축으로 반시계 방향 회전
                    move(cy1, cx1, cy1, cx1 + 1, time, 0);

                    // 아래쪽 칸을 축으로 시계 방향 회전
                    move(cy2, cx2, cy2, cx2 + 1, time, 0);
                }
                
                // 왼쪽 두 칸이 비어 있으면 좌측 이동 및 양쪽 축 회전 가능
                if(cx1 - 1 >= 0 && cx2 - 1 >= 0
                  && board[cy1][cx1 - 1] == 0 && board[cy2][cx2 - 1] == 0) {

                    // 왼쪽 평행 이동
                    move(cy1, cx1 - 1, cy2, cx2 - 1, time, 1);

                    // 위쪽 칸을 축으로 시계 방향 회전
                    move(cy1, cx1 - 1, cy1, cx1, time, 0);

                    // 아래쪽 칸을 축으로 반시계 방향 회전
                    move(cy2, cx2 - 1, cy2, cx2, time, 0);
                }
            }
        }
        
        return answer;
    }
    
    // 이동 또는 회전으로 생성된 새로운 로봇 상태를 방문 처리하고 BFS 큐에 추가
    private void move(int y1, int x1, int y2, int x2, int time, int d) {

        // 두 칸의 연결 방향을 기준으로 동일한 상태의 중복 방문 방지
        if(!visited[y1][x1][d] && !visited[y2][x2][d + 2]) {
            visited[y1][x1][d] = true;
            visited[y2][x2][d + 2] = true;

            // 두 번째 좌표는 방향 d로 복원할 수 있으므로 기준 좌표만 저장
            que.add(new int[]{y1, x1, d, time + 1});
        }            
    }
}