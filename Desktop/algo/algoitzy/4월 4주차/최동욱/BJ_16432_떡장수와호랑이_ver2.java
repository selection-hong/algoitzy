import java.io.*;

public class BJ_16432_떡장수와호랑이_ver2 {

    static int n, c;
    static int[][] arr;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        c = System.in.read();
        n = readInt();

        inputArray();
        int lastPre = solveDP();
        printResult(lastPre);
    }

    private static void inputArray() throws IOException {
        arr = new int[n][11];
        for (int i = 0; i < n; i++) {
            int m = readInt();
            arr[i][0] = m;
            for (int j = 1; j <= m; j++) {
                arr[i][j] = readInt();
            }
        }
    }

    private static int solveDP() {
        dp = new int[n][10];

        for (int i = 1; i <= arr[0][0]; i++) {
            dp[0][arr[0][i]] = -1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= arr[i][0]; j++) {
                int cur = arr[i][j];
                for (int pre = 1; pre <= 9; pre++) {
                    if (cur != pre && dp[i - 1][pre] != 0) {
                        dp[i][cur] = pre;
                        break;
                    }
                }
            }
        }

        for (int i = 1; i <= 9; i++) {
            if (dp[n - 1][i] != 0) {
                return i;
            }
        }
        return 0;
    }

    private static void printResult(int lastPre) {
        if (lastPre == 0) {
            System.out.print(-1);
            return;
        }

        int[] res = new int[n];
        int cur = lastPre;

        for (int i = n - 1; i >= 0; i--) {
            res[i] = cur;
            cur = dp[i][cur];
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(res[i]).append('\n');
        }
        System.out.print(sb.toString());
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