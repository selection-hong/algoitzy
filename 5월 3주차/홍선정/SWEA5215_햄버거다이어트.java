import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA5215_햄버거다이어트 {

    private static int N;
    private static int L;
    private static int[] score;
    private static int[] cal;
    private static int max;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            score = new int[N];
            cal = new int[N];
            max = 0; // 테케마다 전역 변수 초기화

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                score[i] = Integer.parseInt(st.nextToken());
                cal[i] = Integer.parseInt(st.nextToken());
            }

            // 누적값 파라미터 재귀로 구현
            sol(0, 0, 0);

            sb.append("#").append(t).append(" ").append(max).append("\n");
        }

        System.out.print(sb);
    }

    private static void sol(int idx, int sumscore, int sumcal) {
        //가지치기
        if (sumcal > L) {
            return;
        }

        //기저 조건
        if (idx == N) {
            if (sumscore > max) {
                max = sumscore;
            }
            return;
        }

        // 재귀 파트(유도, 분기파트)
        sol(idx + 1, sumscore + score[idx], sumcal + cal[idx]);
        sol(idx + 1, sumscore, sumcal);
    }
}
