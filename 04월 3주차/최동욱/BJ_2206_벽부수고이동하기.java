import java.io.*;
import java.util.*;

public class BJ_2206_벽부수고이동하기 {

    private static class Node {
        int y, x, cnt, crash;

        Node(int y, int x, int cnt, int crash) {
            this.y = y;
            this.x = x;
            this.cnt = cnt;
            this.crash = crash;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        char[][] map = new char[n][];
        for (int y = 0; y < n; y++) {
            String line = br.readLine();
            map[y] = line.toCharArray();
        }

        System.out.println(bfs(map, n, m));
    }

    private static int bfs(char[][] map, int n, int m) {
        boolean[][][] visited = new boolean[n][m][2];
        visited[0][0][0] = true;

        Queue<Node> que = new ArrayDeque<>();
        que.add(new Node(0, 0, 1, 0));

        int[][] dist = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!que.isEmpty()) {
            Node cur = que.poll();
            int cy = cur.y;
            int cx = cur.x;
            int cnt = cur.cnt;
            int crash = cur.crash;

            if (cy == n - 1 && cx == m - 1) return cnt;

            for (int[] d : dist) {
                int ny = cy + d[0];
                int nx = cx + d[1];
                if (check(ny, nx, n, m)) {
                    int nCrash = crash + (map[ny][nx] - '0');
                    if (nCrash < 2 && !visited[ny][nx][nCrash]) {
                        que.add(new Node(ny, nx, cnt + 1, nCrash));
                        visited[ny][nx][nCrash] = true;
                    }
                }
            }
        }

        return -1;
    }

    private static boolean check(int y, int x, int n, int m) {
        return y >= 0 && y < n && x >= 0 && x < m;
    }
}

