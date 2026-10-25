import java.util.*;
import java.io.*;

class Main {

    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        int t = readInt();
        while(t-- > 0) {
            int n = readInt();
            int k = readInt();

            List<Integer>[] list = new ArrayList[n + 1];
            int[] cnt = new int[n + 1];

            for(int i = 1; i <= n; i++) {
                list[i] = new ArrayList<>();
            }

            for(int i = 0; i < n - 1; i++) {
                int u = readInt();
                int v = readInt();

                cnt[u]++;
                cnt[v]++;

                list[u].add(v);
                list[v].add(u);
            }

            sb.append(solve(list, cnt, n, k)).append('\n');
        }

        System.out.println(sb);
    }

    private static int solve(List<Integer>[] list, int[] cnt, int n, int k) {
        if(n == 1 && k > 0) return 0;

        int[] que = new int[n + 1];
        int head = 0, tail = 0;

        for(int i = 1; i <= n; i++) {
            if(cnt[i] == 1) que[tail++] = i;
        }

        while(head < tail && k-- > 0) {
            int len = tail - head;

            while(len-- > 0) {
                int cur = que[head++];

                for(int next : list[cur]) {
                    cnt[next]--;

                    if(cnt[next] == 1) {
                        que[tail++] = next;
                        cnt[next] = 0;
                    }
                }
            }
        }

        return n - head;
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