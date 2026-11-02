import java.util.*;
import java.io.*;

class Main {

    static final int MAX_VALUE = 100;
    
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();
        
        int tc = readInt();

        int[][] visitors = new int[MAX_VALUE][3];
        while(tc-- > 0) {
            int n = readInt();
            int m = readInt();

            for(int i = 0; i < n; i++) {
                visitors[i][0] = readInt();
                visitors[i][1] = readInt();
                visitors[i][2] = readInt();
            }

            Arrays.sort(visitors, 0, n, (a, b) -> { return a[0] - b[0]; });

            boolean res = solve(visitors, n, m);
            sb.append(res ? "YES" : "NO").append('\n');
        }
        System.out.println(sb);
    }

    private static boolean solve(int[][] visitors, int n, int m) {
        int time = 0, tl = m, tr = m;
        for(int i = 0; i < n; i++) {
            int temp = visitors[i][0] - time;
            tl -= temp;
            tr += temp;

            if(check(tl, tr, visitors[i][1], visitors[i][2])) return false;   
            tl = Math.max(tl, visitors[i][1]);
            tr = Math.min(tr, visitors[i][2]);
            time = visitors[i][0];
        }
        return true;
    }

    private static boolean check(int l1, int r1, int l2, int r2) {
        return r2 < l1 || r1 < l2;
    }

    private static int readInt() throws IOException {
        while(c <= ' ') c = System.in.read();
        boolean flag = false;
        if(c == '-') {
            flag = true;
            c = System.in.read();
        }

        int n = 0;
        while(c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return flag ? -n : n;
    }
}