import java.util.*;
import java.io.*;

class Main {

    static int[] stack = new int[200_000];
    static int c, top = -1;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        c = System.in.read();

        int t = readInt();

        while(t-- > 0) {
            int n = readInt();

            Map<Integer, Integer> cnt = new HashMap<>();
            while(n-- > 0) {
                int val = readInt();
                if(!cnt.containsKey(val)) {
                    stack[++top] = val;
                }
                cnt.compute(val, (k, v) -> v == null ? 1 : v + 1);
            }

            while(top > -1) {
                pq.add(cnt.get(stack[top--]));
            }

            while(pq.size() > 1) {
                int v1 = pq.poll() - 1;
                int v2 = pq.poll() - 1;

                if(v1 > 0) pq.add(v1);
                if(v2 > 0) pq.add(v2);
            }

            int res = pq.isEmpty() ? 0 : pq.poll();
            sb.append(res).append('\n');
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