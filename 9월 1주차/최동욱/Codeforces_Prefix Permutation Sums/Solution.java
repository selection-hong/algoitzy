import java.util.*;
import java.io.*;

class Main {

    final static int SIZE = 200_001;
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int[] visited = new int[SIZE];
        int T = readInt();
        for(int t = 1; t <= T; t++) {
            int n = readInt();
            long prefixNum = ((long) n * (n + 1)) >> 1;

            sb.append(solve(visited, t, n, prefixNum)).append('\n');
        }

        System.out.println(sb);
    }

    private static String solve(int[] visited, int t, int n, long prefixNum) throws IOException {
        long pre = 0, keep = 0;
        boolean flag = false, fail = false;
        for(int i = 0; i < n - 1; i++) {
            long num = readLong();
            long diff = num - pre;
            if(diff <= n && visited[(int) diff] < t) {
                visited[(int) diff] = t;
                prefixNum -= diff;
            } else {
                if(flag) {
                    fail = true;
                } else {
                    flag = true;
                    keep = diff;
                }
            }
            pre = num;
        }

        if(fail) return "NO";
        return flag && keep != prefixNum ? "NO" : "YES";
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

    private static long readLong() throws IOException {
        while(c <= ' ') c = System.in.read();
        long n = 0;
        while(c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}