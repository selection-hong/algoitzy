class Solution {
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        int[] parents = new int[n];
        int[] weight = new int[n];
        for(int i = 0; i < n; i++) {
            parents[i] = i;
            weight[i] = -1;
        }

        for(int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            union(u, v, w, parents, weight);
        }

        int m = query.length;
        int[] res = new int[m];
        for(int i = 0; i < m; i++) {
            int pA = find(query[i][0], parents);
            int pB = find(query[i][1], parents);

            res[i] = pA == pB ? weight[pA] : -1;
        }

        return res;
    }

    private void union(int a, int b, int w, int[] parents, int[] weight) {
        int pA = find(a, parents);
        int pB = find(b, parents);

        if(pA != pB) {
            parents[pB] = pA;
        } 

        weight[pA] = weight[pA] & weight[pB] & w;
    }

    private int find(int p, int[] parents) {
        if(p == parents[p]) return p;
        else return parents[p] = find(parents[p], parents);
    }
}