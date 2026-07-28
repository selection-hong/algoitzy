/**
 * [BOJ] 16566 - 카드 게임
 * - 제출 날짜: 2026년 2월 28일
 * - 결과: 맞았습니다!!
 * - 메모리: 33624 KB
 * - 시간: 564 ms
 */

import java.util.*;
import java.io.*;

import java.io.*;

public class Main {

    static int[] parents;
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
        boolean[] check = new boolean[n + 1];
        parents = new int[n + 2];

        for(int i = 0; i < m; i++) check[readInt()] = true;
        for(int i = 0; i < n + 2; i++) parents[i] = i;
        for(int i = 1; i <= n; i++) {
            if(!check[i]) union(i);
        }
    }

    private static String solve(int m, int k) throws IOException {
        StringBuilder sb = new StringBuilder();

        while(k-- > 0) {
            int num = find(readInt() + 1);
            sb.append(num).append('\n');
            union(num);
        }
        return sb.toString();
    }

    private static void union(int idx) {
        parents[idx] = find(idx + 1);
    }

    private static int find(int p) {
        int root = p;
        while (parents[root] != root) {
            root = parents[root];
        }

        while (parents[p] != root) {
            int next = parents[p];
            parents[p] = root;
            p = next;
        }
        return root;
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
