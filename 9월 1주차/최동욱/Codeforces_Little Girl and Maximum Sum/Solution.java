import java.util.*;
import java.io.*;

class Main {

    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();
        int n = readInt();
        int m = readInt();

        int[] arr = new int[n];
        for(int i = 0; i < n; i++) {
            arr[i] = readInt();
        }
        Arrays.sort(arr);
        
        int[] cnt = new int[n + 1];
        while(m-- > 0) {
            int l = readInt() - 1;
            int q = readInt();
            cnt[l]++;
            cnt[q]--;
        }

        for(int i = 1; i <= n; i++) {
            cnt[i] += cnt[i - 1];
        }
        Arrays.sort(cnt);        

        long res = 0;
        while(n > 0) {
            res += (long) cnt[n--] * arr[n];
        }

        System.out.println(res);
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