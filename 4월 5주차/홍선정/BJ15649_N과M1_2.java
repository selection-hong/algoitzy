package SWEA_D2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ15649_N과M1_2{
    static int N, M;
    static int[] arr;
    static boolean[] visited;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[M];
        visited = new boolean[N + 1];

        dfs(0); // 깊이 0부터 탐색 시작
        System.out.println(sb);
    }

    static void dfs(int depth) {
        // 깊이가 M에 도달하면 완성된 수열을 StringBuilder에 추가
        if (depth == M) {
            for (int val : arr) {
                sb.append(val).append(" ");
            }
            sb.append("\n");
            return;
        }

        // 1부터 N까지 탐색
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                visited[i] = true;  // 방문 처리
                arr[depth] = i;     // 현재 깊이에 값 저장
                dfs(depth + 1);     // 다음 깊이 탐색
                visited[i] = false; // 방문 상태 롤백 (백트래킹)
            }
        }
    }
}