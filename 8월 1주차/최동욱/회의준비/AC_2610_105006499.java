/**
 * [BOJ] 2610 - 회의준비
 * - 제출 날짜: 2026년 4월 14일
 * - 결과: 맞았습니다!!
 * - 메모리: 12072 KB
 * - 시간: 92 ms
 */

import java.util.*;
import java.io.*;

class Main {

    static HashMap<Integer, Integer> indexing = new HashMap<>();
    static int[][] times;
    static int[] parents, rank, res;
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();
        int n = readInt();
        int m = readInt();

        init(n);
        setConnect(m);
        floydWarshall(n);        
        
        int num = searchRoot(n);
        solve(n, num);
        System.out.print(buildString(num));
    }

    private static void init(int n) {
        times = new int[n + 1][n + 1];
        parents = new int[n + 1];
        rank = new int[n + 1];

        for(int i = 1; i<= n; i++) {
            parents[i] = i;
            rank[i] = 1;
        }
    }

    private static void setConnect(int m) throws IOException {
        while(m-- > 0) {
            int p1 = readInt();
            int p2 = readInt();
            times[p1][p2] = 1;
            times[p2][p1] = 1;

            union(p1, p2);
        }
    }

    private static void union(int a, int b) {
        int pA = find(a);
        int pB = find(b);

        if(rank[pA] >= rank[pB]) {
            parents[pB] = pA;
            rank[pA] += rank[pB];
        } else {
            parents[pA] = pB;
            rank[pB] += rank[pA];
        }
    }

    private static int find(int p) {
        while(p != parents[p]) {
            parents[p] = parents[parents[p]];
            p = parents[p];
        }
        return p;
    }

    private static int searchRoot(int n) {
        int idx = 0;
        for(int i = 1; i <= n; i++) {
            if(parents[i] == i) 
                indexing.put(i, idx++);
        }
        return idx;
    }

    private static void floydWarshall(int n) {
        for(int k = 1; k <= n; k++) {
            for(int i = 0; i <= n; i++) {
                if(i == k || times[i][k] == 0) continue;
                for(int j = 0; j <= n; j++) {
                    if(i == j || times[k][j] == 0) continue;
                    if(times[i][j] == 0) times[i][j] = times[i][k] + times[k][j];
                    else times[i][j] = Math.min(times[i][j], times[i][k] + times[k][j]);
                }
            }
        }
    }

    private static void solve(int n, int num) {
        res = new int[num];
        int[] cnt = new int[num];
        Arrays.fill(cnt, n + 5);

        for(int i = 1; i <= n; i++) {
            int max = 0;
            int p = indexing.get(find(i));
            for(int j = 1; j <= n; j++) {
                max = Math.max(max, times[i][j]);
            }

            if(cnt[p] > max) {
                res[p] = i;
                cnt[p] = max;
            }
        }
        Arrays.sort(res);
    }

    private static String buildString(int n) {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append('\n');
        for(int i : res) {
            sb.append(i).append('\n');
        }
        return sb.toString();
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