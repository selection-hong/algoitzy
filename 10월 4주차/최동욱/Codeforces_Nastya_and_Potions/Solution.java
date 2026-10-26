import java.util.*;
import java.io.*;

class Main {

    static int c;

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int t = readInt();
        while (t-- > 0) {
            int n = readInt();
            int k = readInt();

            List<Integer>[] dependents = new ArrayList[n + 1];
            long[] cost = new long[n + 1];

            inputCost(dependents, cost, n);
            markFreePotions(cost, k);

            int[] indegree = new int[n + 1];
            inputRecipe(dependents, indegree, n);

            topologicalSort(dependents, indegree, cost, n);

            for (int i = 1; i <= n; i++) {
                sb.append(cost[i]).append(' ');
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }

    private static void inputCost(List<Integer>[] dependents, long[] cost, int n) throws IOException {
        for (int i = 1; i <= n; i++) {
            dependents[i] = new ArrayList<>();
            cost[i] = readInt();
        }
    }

    private static void markFreePotions(long[] cost, int k) throws IOException {
        for (int i = 0; i < k; i++) {
            int idx = readInt();
            cost[idx] = 0;
        }
    }

    private static void inputRecipe(List<Integer>[] dependents, int[] indegree, int n) throws IOException {
        for (int i = 1; i <= n; i++) {
            int len = readInt();
            indegree[i] = len;

            while (len-- > 0) {
                int val = readInt();
                dependents[val].add(i);
            }
        }
    }

    private static void topologicalSort(List<Integer>[] dependents, int[] indegree, long[] cost, int n) {
        long[] mixCost = new long[n + 1];
        int[] que = new int[n + 1];
        int head = 0, tail = 0;

        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                que[tail++] = i;
            }
        }

        while (head < tail) {
            int cur = que[head++];

            for (int child : dependents[cur]) {
                indegree[child]--;
                mixCost[child] += cost[cur];

                if (indegree[child] == 0) {
                    cost[child] = Math.min(cost[child], mixCost[child]);
                    que[tail++] = child;
                }
            }
        }
    }

    private static int readInt() throws IOException {
        while (c <= ' ') c = System.in.read();

        int n = 0;
        while (c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}