import java.util.*;
import java.io.*;

public class Main {

    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int n = readInt();
        int m = readInt();

        int[] parents = new int[n + 1];
        int[] cnt = new int[n + 1];
        Integer[] arr = new Integer[n + 1];

        for(int i = 0; i <= n; i++) {
            parents[i] = i;
            arr[i] = 0;
        }
        cnt[0] = -1;

        int connect = 1, group = n;
        while(m-- > 0) {
            int x = readInt();
            int y = readInt();

            int pX = find(parents, x);
            int pY = find(parents, y);

            if(pX != pY) {
                changeValue(arr, cnt[pX], cnt[pY] + cnt[pX] + 1);
                changeValue(arr, cnt[pY], -1);
                Arrays.sort(arr, 0, group, Collections.reverseOrder());

                parents[pX] = pY;
                cnt[pY] += cnt[pX] + 1;
                
                group--;
            } else {
                connect++;
            }
            
            sb.append(countConnect(arr, Math.min(group, connect))).append('\n');
        }

        System.out.println(sb);
    }

    private static int find(int[] parents, int p) {
        if(parents[p] == p) return p;
        else return parents[p] = find(parents, parents[p]);
    }

    private static void changeValue(Integer[] arr, int target, int value) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == target) {
                arr[i] = value;
                break;
            }
        }
    }

    private static int countConnect(Integer[] arr, int len) {
        int conn = 0;
        for(int i = 0; i < len; i++) {
            conn += arr[i] + 1;
        }
        return conn - 1;
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