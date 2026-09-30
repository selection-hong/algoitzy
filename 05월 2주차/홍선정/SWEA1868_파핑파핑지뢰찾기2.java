package SWEA_D2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class SWEA1868_파핑파핑지뢰찾기2 {
    static int N;
    static char[][] board;
    static int[][] mineCnt;
    static boolean[][] visited;
    
    // 8방향 탐색 (상, 하, 좌, 우, 대각선 4개)
    static int[] dr = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int[] dc = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int T = Integer.parseInt(br.readLine().trim());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine().trim());
            
            board = new char[N][N];
            mineCnt = new int[N][N];
            visited = new boolean[N][N];

            // 1. 보드 상태 입력
            for (int i = 0; i < N; i++) {
                board[i] = br.readLine().toCharArray();
            }

            // 2. 각 칸의 주변 지뢰 개수 미리 계산
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] == '.') {
                        mineCnt[i][j] = countMines(i, j);
                    }
                }
            }

            int clicks = 0;

            // 3. (핵심) 주변 지뢰가 0인 칸을 '먼저' 찾아 연쇄 폭발(BFS) 실행
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] == '.' && mineCnt[i][j] == 0 && !visited[i][j]) {
                        bfs(i, j);
                        clicks++; // 0인 덩어리당 1번의 클릭 발생
                    }
                }
            }

            // 4. 연쇄 폭발로 열리지 않은 나머지 칸들(0이 아닌 칸) 카운트
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] == '.' && !visited[i][j]) {
                        clicks++; // 이 칸들은 개별적으로 1번씩 클릭해야 함
                    }
                }
            }

            sb.append("#").append(t).append(" ").append(clicks).append("\n");
        }
        System.out.print(sb.toString());
    }

    // 현재 위치 주변 8방향의 지뢰 개수를 세는 메서드
    static int countMines(int r, int c) {
        int cnt = 0;
        for (int i = 0; i < 8; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            
            // 배열 범위를 벗어나지 않고, 해당 위치가 지뢰라면 카운트 증가
            if (nr >= 0 && nr < N && nc >= 0 && nc < N && board[nr][nc] == '*') {
                cnt++;
            }
        }
        return cnt;
    }

    // 연쇄 폭발을 처리하는 BFS
    static void bfs(int r, int c) {
        // LinkedList보다 ArrayDeque가 캐시 지역성이 좋아 속도가 미세하게 더 빠름
        Queue<int[]> q = new ArrayDeque<>(); 
        q.offer(new int[]{r, c});
        visited[r][c] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cr = cur[0];
            int cc = cur[1];

            // 💡 현재 칸이 0일 때만 주변 8방향을 마저 열어준다.
            // (만약 0이 아니라면 자기 자신만 열리고 더 이상 연쇄 폭발이 일어나지 않음)
            if (mineCnt[cr][cc] == 0) {
                for (int i = 0; i < 8; i++) {
                    int nr = cr + dr[i];
                    int nc = cc + dc[i];
                    
                    if (nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc] && board[nr][nc] == '.') {
                        visited[nr][nc] = true;
                        q.offer(new int[]{nr, nc});
                    }
                }
            }
        }
    }
}