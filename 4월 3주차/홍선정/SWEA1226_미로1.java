package SWEA_D2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class SWEA1226_미로1 {
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static int[][] map;

    public static void main(String[] args) throws Exception {
   
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for (int t = 1; t <= 10; t++) {
            br.readLine();
            
            map = new int[16][16];
            int startR = 0, startC = 0;

            for (int i = 0; i < 16; i++) {
                String line = br.readLine();
                for (int j = 0; j < 16; j++) {
                    map[i][j] = line.charAt(j) - '0';
                    if (map[i][j] == 2) {
                        startR = i;
                        startC = j;
                    }
                }
            }

            int result = bfs(startR, startC);
            sb.append("#").append(t).append(" ").append(result).append("\n");
        }
        
        System.out.print(sb.toString());
    }

    static int bfs(int r, int c) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[16][16];

        queue.offer(new int[]{r, c});
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int cr = current[0];
            int cc = current[1];

            if (map[cr][cc] == 3) {
                return 1;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cr + dr[d];
                int nc = cc + dc[d];

                if (nr >= 0 && nr < 16 && nc >= 0 && nc < 16) {
                    if (!visited[nr][nc] && map[nr][nc] != 1) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        return 0;
    }
}