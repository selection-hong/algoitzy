import java.io.*;
import java.util.*;

public class BJ_1584_게임_Dijkstra {

    private static class Node implements Comparable<Node> {
        int y, x, t;

        public Node(int y, int x, int t) {
            this.y = y;
            this.x = x;
            this.t = t;
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(this.t, n.t);
        }
    }
    
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();
        int[][] arr = new int[501][501];
        int n = readInt();
        inputArea(arr, n, 1);

        int m = readInt();
        inputArea(arr, m, -1);        

        System.out.println(dijkstra(arr, 0, 500));
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

    private static int dijkstra(int[][] arr, int start, int end) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, start, 0));
        arr[start][start] = -1;

        int[][] visited = new int[end + 1][end + 1];
        int INF = end * end + 5;
        for(int i = 0; i <= end; i++) {
            for(int j = 0; j <= end; j++) {
                visited[i][j] = INF;
            }
        }
        visited[start][start] = 0;

        int[][] dist = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            int cy = cur.y;
            int cx = cur.x;
            int t = cur.t;
            if(cy == end && cx == end) return t;
            if(t > visited[cy][cx]) continue;

            for(int[] d : dist) {
                int ny = cy + d[0];
                int nx = cx + d[1];
                
                if(check(ny, nx, end) && arr[ny][nx] > -1 && visited[ny][nx] > t + arr[ny][nx]) {
                    pq.add(new Node(ny, nx, t + arr[ny][nx]));
                    visited[ny][nx] = t + arr[ny][nx];
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
