import java.util.*;
import java.io.*;

class Solution {

    static final int MAX_SIZE = 100_000;
    
    static List<Integer>[] childs = new ArrayList[MAX_SIZE + 1];
    static {
        for(int i = 1; i <= MAX_SIZE; i++) {
            childs[i] = new ArrayList<>();
        }
    }

    static int[] parent = new int[MAX_SIZE + 1];
    static int[] cnt = new int[MAX_SIZE + 1];
    static int[] que = new int[MAX_SIZE + 1];
    
    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();
        int T = readInt();

        for(int t = 1; t <= T; t++) {
            int n = readInt();
            int m = readInt();

            // init
            for(int i = 1; i <= n; i++) {
                childs[i].clear();
                cnt[i] = 0;
            }

            inputNodes((n - 1) + m);
            topologicalSort(n);
            sb.append('#').append(t);
            buildString(sb, n);
        }
        System.out.print(sb);
    }

    private static void inputNodes(int n) throws IOException {
        while(n-- > 0) {
            int u = readInt();
            int v = readInt();

            childs[u].add(v);
            cnt[v]++;
        }
    }

    private static void topologicalSort(int n) {
        int head = 0, tail = 0;
        for(int i = 1; i <= n; i++) {
            if(cnt[i] == 0) {
                parent[i] = 0;
                que[tail++] = i;
            }
        }

        while(head < tail) {
            int p = que[head++];

            for(int child : childs[p]) {
                cnt[child]--;
                if(cnt[child] == 0) {
                    que[tail++] = child;
                    parent[child] = p;
                }
            }
        }
    }

    private static void buildString(StringBuilder sb, int n) {
        for(int i = 1; i <= n; i++) {
            sb.append(' ').append(parent[i]);
        }
        sb.append('\n');
    }

    private static int readInt() throws IOException {
        while(c <= ' ') c = System.in.read();
        int n = 0;
        while(c >= '0' &&  c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}