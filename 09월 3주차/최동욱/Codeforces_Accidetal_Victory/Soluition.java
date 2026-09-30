import java.util.*;
import java.io.*;
 
public class Main {
 
    final static int INF = 200_000;
 
    static int[][] arr = new int[INF][2];
    static int[] stack = new int[INF];
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();
        
        int t = readInt();
        while(t-- > 0) {
            int n = readInt();
            int idx = 0;
            while(idx < n) {
                arr[idx][0] = readInt();
                arr[idx++][1] = idx;
            }
 
            Arrays.sort(arr, 0, n, (a, b) -> {
                return a[0] - b[0];
            });
            
            long sum = arr[0][0]; int top = 0;
            stack[top++] = arr[0][1];
            for(int i = 1; i < n; i++) {
                if(sum < arr[i][0]) {
                    top = 0;
                }
 
                sum += arr[i][0];
                stack[top++] = arr[i][1];
            }
 
            Arrays.sort(stack, 0, top);
 
            sb.append(top).append('\n');
            for(int i = 0; i < top; i++) {
                sb.append(stack[i]).append(' ');
            }
            sb.append('\n');
        }
        System.out.println(sb);
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