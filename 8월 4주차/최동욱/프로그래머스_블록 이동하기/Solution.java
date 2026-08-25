import java.util.*;

class Solution {

    static Queue<int[]> que = new ArrayDeque<>();
    static boolean[][][] visited; // [y][x][0: 가로, 1: 세로]
    static int n;

    public int solution(int[][] board) {
        int answer = -1;
        n = board.length;

        // {기준 y, 기준 x, dir, time}
        // dir: 0 가로, 1 세로
        // 가로 상태의 기준 좌표는 왼쪽 칸
        // 세로 상태의 기준 좌표는 위쪽 칸
        que.add(new int[]{0, 0, 0, 0});

        visited = new boolean[n][n][2];
        visited[0][0][0] = true;

        while (!que.isEmpty()) {
            int[] cur = que.poll();

            int cy1 = cur[0];
            int cx1 = cur[1];
            int d = cur[2];
            int time = cur[3];

            // 기준 좌표와 방향을 이용해 로봇의 두 번째 칸 계산
            int cy2 = cy1 + d;
            int cx2 = cx1 + (d ^ 1);

            if ((cy1 == n - 1 && cx1 == n - 1)
                    || (cy2 == n - 1 && cx2 == n - 1)) {
                return time;
            }

            // 가로 상태
            if (d == 0) {

                // 좌측 평행 이동
                if (cx1 - 1 >= 0 && board[cy1][cx1 - 1] == 0) {
                    move(cy1, cx1 - 1, 0, time);
                }

                // 우측 평행 이동
                if (cx2 + 1 < n && board[cy2][cx2 + 1] == 0) {
                    move(cy1, cx1 + 1, 0, time);
                }

                // 아래쪽 두 칸이 비어 있으면 아래 이동 및 양쪽 축 회전 가능
                if (cy1 + 1 < n && board[cy1 + 1][cx1] == 0 && board[cy2 + 1][cx2] == 0) {

                    // 아래 평행 이동
                    move(cy1 + 1, cx1, 0, time);

                    // 왼쪽 칸을 축으로 시계 방향 회전
                    move(cy1, cx1, 1, time);

                    // 오른쪽 칸을 축으로 반시계 방향 회전
                    move(cy1, cx2, 1, time);
                }

                // 위쪽 두 칸이 비어 있으면 위 이동 및 양쪽 축 회전 가능
                if (cy1 - 1 >= 0 && board[cy1 - 1][cx1] == 0 && board[cy2 - 1][cx2] == 0) {

                    // 위 평행 이동
                    move(cy1 - 1, cx1, 0, time);

                    // 왼쪽 칸을 축으로 반시계 방향 회전
                    move(cy1 - 1, cx1, 1, time);

                    // 오른쪽 칸을 축으로 시계 방향 회전
                    move(cy1 - 1, cx2, 1, time);
                }

            // 세로 상태
            } else {

                // 위쪽 평행 이동
                if (cy1 - 1 >= 0 && board[cy1 - 1][cx1] == 0) {
                    move(cy1 - 1, cx1, 1, time);
                }

                // 아래쪽 평행 이동
                if (cy2 + 1 < n && board[cy2 + 1][cx2] == 0) {
                    move(cy1 + 1, cx1, 1, time);
                }

                // 오른쪽 두 칸이 비어 있으면 우측 이동 및 양쪽 축 회전 가능
                if (cx1 + 1 < n && board[cy1][cx1 + 1] == 0 && board[cy2][cx2 + 1] == 0) {

                    // 오른쪽 평행 이동
                    move(cy1, cx1 + 1, 1, time);

                    // 위쪽 칸을 축으로 반시계 방향 회전
                    move(cy1, cx1, 0, time);

                    // 아래쪽 칸을 축으로 시계 방향 회전
                    move(cy2, cx1, 0, time);
                }

                // 왼쪽 두 칸이 비어 있으면 좌측 이동 및 양쪽 축 회전 가능
                if (cx1 - 1 >= 0 && board[cy1][cx1 - 1] == 0 && board[cy2][cx2 - 1] == 0) {

                    // 왼쪽 평행 이동
                    move(cy1, cx1 - 1, 1, time);

                    // 위쪽 칸을 축으로 시계 방향 회전
                    move(cy1, cx1 - 1, 0, time);

                    // 아래쪽 칸을 축으로 반시계 방향 회전
                    move(cy2, cx1 - 1, 0, time);
                }
            }
        }

        return answer;
    }

    // 이동 또는 회전으로 생성된 새로운 로봇 상태를 방문 처리하고 BFS 큐에 추가
    private void move(int y, int x, int d, int time) {

        // 가로 상태는 왼쪽 칸, 세로 상태는 위쪽 칸을 기준 좌표로 사용하므로
        // 동일한 로봇 상태를 하나의 기준 좌표와 방향만으로 관리 가능
        if (!visited[y][x][d]) {
            visited[y][x][d] = true;
            que.add(new int[]{y, x, d, time + 1});
        }
    }
}
