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
        int[] freq = new int[n + 1];
        freq[0] = n;

        for(int i = 0; i <= n; i++) parents[i] = i;

        int conn = 1, val = 0;
        while(m-- > 0) {
            int x = readInt();
            int y = readInt();

            int pX = find(parents, x);
            int pY = find(parents, y);

            if(pX != pY) {
                int temp = cnt[pX] + cnt[pY] + 1;                
                freq[cnt[pX]]--;
                freq[cnt[pY]]--;
                freq[temp]++;

                parents[pX] = pY;
                cnt[pY] = temp;

                if(val < temp) {
                    val = temp;
                }
            } else {
                conn++;
            }
            
            sb.append(countConnect(freq, conn, val)).append('\n');
        }

        System.out.println(sb);
    }

    private static int find(int[] parents, int p) {
        if(parents[p] == p) return p;
        else return parents[p] = find(parents, parents[p]);
    }

    private static int countConnect(int[] freq, int target, int len) {
        int val = 0;
        for(int i = len; i >= 0; i--) {
            if(target >= freq[i]) {
                val += freq[i] * (i + 1);
                target -= freq[i];
            } else {
                val += target * (i + 1);
                break;
            }
        }
        return val - 1;
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