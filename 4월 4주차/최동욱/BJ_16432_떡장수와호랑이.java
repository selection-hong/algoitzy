import java.io.*;

public class BJ_16432_떡장수와호랑이 {

    static int[][] arr;          // 날짜별 떡 종류
    static int[] res;            // 결과값 저장
    static boolean[][] visited;  // 방문 체크
    static int c;
    static boolean flag = false; // 종료 조건

    public static void main(String[] args) throws IOException {
        c = System.in.read();
        int n = readInt();

        // 날짜별 떡 저장
        inputArray(n);

        // 배열 초기화
        res = new int[n];
        visited = new boolean[n][11];

        // dfs 탐색
        dfs(0, 0, n);

        // flag == true : 떡을 줄 방법이 있음
        System.out.print(flag ? buildString() : -1);
    }

    // 날짜별 떡 종류 저장
    private static void inputArray(int n) throws IOException {
        arr = new int[n][11];
        for (int i = 0; i < n; i++) {
            int m = readInt();
            arr[i][0] = m; // 0번째 값을 idx의 수로 사용
            for (int day = 1; day <= m; day++) {
                arr[i][day] = readInt();
            }
        }
    }

    // DFS - 백트래킹
    private static void dfs(int day, int pre, int n) {
        if (day == n) {
            flag = true;
            return;
        }

        for (int i = 1; i <= arr[day][0]; i++) {
            int num = arr[day][i];

            // 전날 준 떡과 같은 종류거나, 이미 탐색한 경로라면 continue
            if (num == pre || visited[day][num]) continue;

            res[day] = num;           // 호랑이에게 준 떡
            dfs(day + 1, num, n);     // 재귀
            if (flag) break;           // 조기 종료
            visited[day][num] = true; // 방문 체크
        }
    }

    // 출력문 생성
    private static String buildString() {
        StringBuilder sb = new StringBuilder();
        for (int i : res) {
            sb.append(i).append('\n');
        }
        return sb.toString();
    }

    // FastIO
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