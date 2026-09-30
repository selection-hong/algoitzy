/**
 * [BOJ] 16566 - 카드 게임
 * - 제출 날짜: 2026년 2월 28일
 * - 결과: 맞았습니다!!
 * - 메모리: 46524 KB
 * - 시간: 720 ms
 */

import java.io.*;
import java.util.*;

public class AC_16566_103366777 {

    static int[] arrIdx;
    static int[] min_su;
    static int c;

    public static void main(String[] args) throws IOException {
        c = System.in.read();
        int n = readInt();
        int m = readInt();
        int k = readInt();

        init(n, m);
        System.out.print(solve(m, k));
    }

    private static void init(int n, int m) throws IOException {
        arrIdx = new int[n + 1];
        min_su = new int[m--];

        for(int i = 0; i <= m; i++) arrIdx[readInt()] = n + 2;
        for(int i = n; i > 0 && m >= 0; i--) {
            if(arrIdx[i] == n + 2) {
                min_su[m] = i; // value
                arrIdx[i] = m--; // 구간
            }
        }
    }

    private static String solve(int m, int k) throws IOException {
        StringBuilder sb = new StringBuilder();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(min_su[m-1], min_su[0]);

        while(k-- > 0) {
            int num = readInt();
            int endVal = map.ceilingKey(num + 1);
            int startVal = map.get(endVal);

            int endIdx = arrIdx[endVal];
            int startIdx = arrIdx[startVal];

            int idx = binarySearch(startIdx, endIdx, num + 1);
            sb.append(min_su[idx]).append('\n');

            if(startIdx <= idx - 1) map.put(min_su[idx - 1], startVal);
            if(endIdx >= idx + 1) map.put(endVal, min_su[idx + 1]);
            else map.remove(endVal);
        }
        return sb.toString();
    }

    private static int binarySearch(int left, int right, int target) {
        int res = right;
        while(left <= right) {
            int mid = (left + right) / 2;

            if (min_su[mid] >= target) {
                res = mid;
                right = mid - 1;
            }  else {
                left = mid + 1;
            }
        }
        return res;
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
