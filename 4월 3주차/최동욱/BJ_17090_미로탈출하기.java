import java.io.*;

public class BJ_17090_미로탈출하기 {

    final static int[][] DIR = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    static int[][] map, visited;
    static int c;

    public static void main(String[] args) throws IOException {
        c = System.in.read();
        int n = readInt();
        int m = readInt();

        map = new int[n][m];
        visited = new int[n][m];
        inputMap(n, m);
        System.out.println(solve(n, m));
    }

    private static int[][] inputMap(int n, int m) throws IOException {
        for (int y = 0; y < n; y++) {
            while (c <= ' ') c = System.in.read();
            for (int x = 0; x < m; x++) {
                map[y][x] = inputDirt(c);
                c = System.in.read();
            }
        }
        return map;
    }

    private static int inputDirt(int val) {
        if (val == 'D') return 0;
        else if (val == 'U') return 1;
        else if (val == 'R') return 2;
        else return 3;
    }

    private static int solve(int n, int m) {
        int res = 0;
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (visited[y][x] == 0) {
                    res += dfs(y, x, n, m);
                }
            }
        }
        return res;
    }

    private static int dfs(int y, int x, int n, int m) {
        visited[y][x] = -1;
        int[] d = DIR[map[y][x]];
        int ny = y + d[0];
        int nx = x + d[1];

        if (!check(ny, nx, n, m)) {
            visited[y][x] = 1;
            return 1;
        }

        if (visited[ny][nx] == 0) {
            int val = dfs(ny, nx, n, m);
            if (val == 0) return 0;
            else {
                visited[y][x] = 1;
                return val + 1;
            }
        } else if (visited[ny][nx] == 1) {
            visited[y][x] = 1;
            return 1;
        } else {
            return 0;
        }
    }

    private static boolean check(int y, int x, int n, int m) {
        return y >= 0 && y < n && x >= 0 && x < m;
    }

    private static int readInt() throws IOException {
        while (c <= ' ') c = System.in.read();
        int n = 0;
        while (c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}
