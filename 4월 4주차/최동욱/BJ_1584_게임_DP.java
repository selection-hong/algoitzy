import java.io.*;
import java.util.*;

public class BJ_1584_게임_01_BFS {
    
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();
        int[][] arr = new int[501][501];
        int n = readInt();
        inputArea(arr, n, 1);

        int m = readInt();
        inputArea(arr, m, -1);        

        System.out.println(bfs(arr, 0, 500));
    }

    private static void inputArea(int[][] arr, int n, int w) throws IOException {
        while(n-- > 0) {
            int x1 = readInt();
            int y1 = readInt();

            int x2 = readInt();
            int y2 = readInt();

            int sX = Math.min(x1, x2);
            int eX = Math.max(x1, x2);

            int sY = Math.min(y1, y2);
            int eY = Math.max(y1, y2);

            for(int x = sX; x <= eX; x++) {
                for(int y = sY; y <= eY; y++) {
                    arr[x][y] = w;
                }
            }
        }
    }

    private static int bfs(int[][] arr, int start, int end) {
        Deque<int[]> deque = new ArrayDeque<>();
        deque.add(new int[]{start, start, 0});
        arr[start][start] = -1;

        int[][] dist = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        while(!deque.isEmpty()) {
            int[] cur = deque.pollFirst();
            int cy = cur[0];
            int cx = cur[1];
            int t = cur[2];
            if(cy == end && cx == end) return t;

            for(int[] d : dist) {
                int ny = cy + d[0];
                int nx = cx + d[1];

                if(check(ny, nx, end)) {
                    if(arr[ny][nx] == -1) continue;
                    else if(arr[ny][nx] == 0) {
                        deque.addFirst(new int[]{ny, nx, t});
                    } else {
                        deque.addLast(new int[]{ny, nx, t + 1});
                    }
                    arr[ny][nx] = -1;
                }
            }
        }

        return -1;
    }

    private static boolean check(int y, int x, int n) {
        return y >= 0 && y <= n && x >= 0 && x <= n;
    }

    private static int readInt() throws IOException {
        while(c <= ' ') c = System.in.read();
        int n = 0;
        while(c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}
