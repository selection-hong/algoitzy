/**
 * [BOJ] 3144 - 자석
 * - 제출 날짜: 2026년 1월 22일
 * - 결과: 틀렸습니다
 */

import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        int[] arr = inputArray(n, st, br);
        System.out.println(solve(arr, n, l));
    }

    private static int[] inputArray(int n, StringTokenizer st, BufferedReader br) throws IOException {
        int[] arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            arr[i] = st.nextToken().equals("NS") ? 1 : 0;
        }
        return arr;
    }

    private static int solve(int[] arr, int n, int l) {
        int[] cnt = new int[2];
        int left = 0, right = 0, total = 0;
        int minSwap = Integer.MAX_VALUE;

        while(left < n) {
            while(total < l && right < n) {
                int target = arr[right];
                while(right < n && target == arr[right]) {
                    right++;
                    total++;
                }
                cnt[target]++;
            }

            if(total == l) {
                minSwap = Math.min(minSwap, Math.min(cnt[0], cnt[1]));
            }

            int target = arr[left];
            while(left < n && target == arr[left]) {
                left++;
                total--;
            }
            cnt[target]--;
        }
        return minSwap;
    }
}