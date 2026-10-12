import java.util.*;
import java.io.*;

class Main {

    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();

        int n = readInt();
        int m = readInt();

        ArrayList<Integer>[] list = settingList(n, m);

        System.out.println(solve(list, n));
    }

    private static ArrayList<Integer>[] settingList(int n, int m) throws IOException {
        ArrayList<Integer>[] list = new ArrayList[n + 1];
        
        for(int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
        }

        while(m-- > 0) {
            int u = readInt();
            int v = readInt();

            list[u].add(v);
            list[v].add(u);
        }

        return list;
    }

    private static String solve(ArrayList<Integer>[] list, int n) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(
            (a, b) -> list[b].size() - list[a].size()
        );

        int[] parents = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            parents[i] = i;
            pq.add(i);
        }

        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        while(cnt < n - 1) {
            int cur = pq.poll();

            for(int next : list[cur]) {
                if(union(parents, cur, next)) {
                    sb.append(cur).append(' ').append(next).append('\n');
                    cnt++;
                }
            }
        }

        return sb.toString();
    }

    private static boolean union(int[] parents, int a, int b) {
        int pA = find(parents, a);
        int pB = find(parents, b);

        if(pA != pB) {
            parents[pB] = pA;
        } 

        return pA != pB;
    }

    private static int find(int[] parents, int p) {
        if(parents[p] == p) return p;
        else return parents[p] = find(parents, parents[p]);
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