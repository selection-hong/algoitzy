import java.util.*;
import java.io.*;

class Solution {

    static int[] arr = new int[10_000];
    static int[] num = new int[2];
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();
        int T = readInt();
        for(int t = 1; t <= T; t++) {
            int n = readInt();
            for(int i = 0; i < n; i++) arr[i] = readInt();

            Arrays.sort(arr, 0, n);

            num[0] = num[1] = arr[0];
            int res = 0;

            for(int i = 1; i < n; i++) {
                if(res < arr[i] - num[i & 1]) res = arr[i] - num[i & 1];
                num[i & 1] = arr[i];
            }

            sb.append('#').append(t).append(' ').append(res).append('\n');
        }
        System.out.print(sb);
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