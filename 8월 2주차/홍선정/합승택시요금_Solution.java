import java.util.*;

class Solution {

    static class N implements Comparable<N> {
        int v, w;

        N(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(N o) {
            return Integer.compare(this.w, o.w);
        }
    }

    List<List<N>> g;
    static final int INF = Integer.MAX_VALUE;

    public int solution(int n, int s, int a, int b, int[][] fs) {
        g = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            g.add(new ArrayList<>());
        }
        for (int[] f : fs) {
            int u = f[0];
            int v = f[1];
            int w = f[2];

            g.get(u).add(new N(v, w));
            g.get(v).add(new N(u, w));
        }

        int[] ds = dj(n, s);
        int[] da = dj(n, a);
        int[] db = dj(n, b);

        int ans = INF;

        // 합승 후 갈라지는 지점
        for (int k = 1; k <= n; k++) {
            ans = Math.min(ans, ds[k] + da[k] + db[k]);
        }

        return ans;
    }

    private int[] dj(int n, int st) {
        int[] d = new int[n + 1];
        Arrays.fill(d, INF);

        PriorityQueue<N> pq = new PriorityQueue<>();
        d[st] = 0;
        pq.offer(new N(st, 0));

        while (!pq.isEmpty()) {
            N c = pq.poll();

            // 이미 더 짧은 경로가 발견된 경우
            if (c.w > d[c.v]) {
                continue;
            }

            for (N nx : g.get(c.v)) {
                int nw = c.w + nx.w;

                if (nw < d[nx.v]) {
                    d[nx.v] = nw;
                    pq.offer(new N(nx.v, nw));
                }
            }
        }

        return d;
    }
}
