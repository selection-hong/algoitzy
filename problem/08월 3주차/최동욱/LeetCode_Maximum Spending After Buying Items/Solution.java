class Solution {
    public long maxSpending(int[][] values) {

        int n = values.length;
        int m = values[0].length;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[0] - b[0];
        });

        int[] idx = new int[n]; 

        for(int i = 0; i < n; i++) {
            idx[i] = m - 2;
            pq.add(new int[]{values[i][m - 1], i});
        }

        long res = 0;
        for(int d = 1; d <= n * m; d++) {
            int[] cur = pq.poll();
            long val = cur[0];
            int i = cur[1];

            res += val * d;
            if(idx[i] >= 0) {
                pq.add(new int[]{values[i][idx[i]--], i});
            }
        }

        return res;
    }
}