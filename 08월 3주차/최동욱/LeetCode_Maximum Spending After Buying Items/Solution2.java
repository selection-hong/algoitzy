class Solution {
    public long maxSpending(int[][] values) {

        int n = values.length;
        int m = values[0].length;

        int[] idx = new int[n]; 
        for(int i = 0; i < n; i++) {
            idx[i] = m - 1;
        }
        
        long res = 0;
        for(int d = 1; d <= n * m; d++) {
            long min = 1_000_005;
            int minIdx = -1;

            for(int i = 0; i < n; i++) {
                if(idx[i] >= 0 && values[i][idx[i]] < min) {
                    min = values[i][idx[i]];
                    minIdx = i;
                }
            }

            idx[minIdx]--;
            res += min * d;
        }

        return res;
    }
}