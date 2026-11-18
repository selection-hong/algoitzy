import java.io.*;

public class BJ_1600_말이되고픈원숭이 {

    final static int BIT_SHIFT = 8;
    final static int[][] DICT = {
            {1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {2, 1}, {-2, 1}, {2, -1}, {-2, -1},
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    static int[] arr;
    static int[] dequeXY, dequeKM, visited;
    static int c, k, row, col, head = 0, tail = 0;

    public static void main(String[] args) throws IOException {
        c = System.in.read();
        k = readInt() + 1;
        col = readInt();
        row = readInt();
        arr = new int[(row << BIT_SHIFT) | col];

        inputArray();
        int[] visited = new int[(row << BIT_SHIFT) | col];

        System.out.println(bfs());
    }

    private static void inputArray() throws IOException {
        for(int y = 0; y < row; y++) {
            for(int x = 0; x < col; x++) {
                arr[(y << BIT_SHIFT) | x] = readInt();
            }
        }
    }

    private static int bfs() {
        visited = new int[(row << BIT_SHIFT) | col];
        visited[0] = k + 1;

        dequeXY = new int[row * col * (k + 1)];
        dequeKM = new int[row * col * (k + 1)];

        dequeXY[tail] = 0;
        dequeKM[tail] = k;
        tail++;

        while(head < tail) {
            int cur = dequeXY[head];
            int cur2 = dequeKM[head];
            head++;

            int y = cur >> BIT_SHIFT;
            int x = cur & ((1 << BIT_SHIFT) - 1);
            int nk = cur2 & ((1 << BIT_SHIFT) - 1);
            int nm = (cur2 >> BIT_SHIFT);

            if(y == row - 1 && x == col - 1) {
                return nm;
            }

            move(y, x, 8, 12, nk, nm);
            if(nk > 1) {
                move(y, x, 0, 8, nk - 1, nm);
            }
        }

        return -1;
    }

    private static void move(int y, int x, int s, int e, int w, int m) {
        for(int i = s; i < e; i++) {
            int ny = y + DICT[i][0];
            int nx = x + DICT[i][1];
            int cur = (ny << BIT_SHIFT) | nx;
            if(check(ny, nx) && arr[cur] == 0 && visited[cur] < w) {
                visited[cur] = w;
                dequeXY[tail] = cur;
                dequeKM[tail] = ((m + 1) << BIT_SHIFT) | w;
                tail++;
            }
        }
    }

    private static boolean check(int y, int x) {
        return y >= 0 && y < row && x >= 0 && x < col;
    }

    private static int readInt() throws IOException {
        while(c <= ' ') c = System.in.read();
        int n = 0;
        while(c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c - '0');
            c = System.in.read();
        }
        return n;
    }
}