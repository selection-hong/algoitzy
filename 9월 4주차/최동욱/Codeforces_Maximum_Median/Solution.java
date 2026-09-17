import java.util.*;
import java.io.*;
 
public class Main {
 
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();
 
        int n = readInt();
        int k = readInt();
 
        int[] arr = new int[n];
        for(int i = 0; i < n; i++) {
            arr[i] = readInt();
        }
 
        Arrays.sort(arr);
 
        int half = n >> 1;
        long left = 0, right = arr[half] + k;
 
        while(left < right) {
            long mid = (left + right + 1) >> 1;
 
            long cost = 0;
            for (int i = half; i < n; i++) {
                if (arr[i] < mid) {
                    cost += mid - arr[i];
                } else break;
            }
 
            if(k >= cost) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
 
        System.out.println(left);
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