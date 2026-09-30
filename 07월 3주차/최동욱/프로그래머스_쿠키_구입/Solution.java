class Solution {
    public int solution(int[] cookie) {
        int n = cookie.length;
        if (n == 1) return 0;

        for (int i = 1; i < n; i++) {
            cookie[i] += cookie[i - 1];
        }

        int res = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int r = i + 1; r < n; r++) {
                int secondSum = cookie[r] - cookie[i];

                if (secondSum <= res) continue;

                if (binarySearch(cookie, i, secondSum)) {
                    res = secondSum;
                }
            }
        }

        return res;
    }

    private boolean binarySearch(int[] prefixSum, int end, int target) {
        int left = 0, right = end;
        
        while (left <= right) {
            int mid = (left + right) >> 1;
            int firstSum = mid == 0 ? prefixSum[end] : prefixSum[end] - prefixSum[mid - 1];
            
            if (firstSum == target) {
                return true;
            }
            
            if (firstSum > target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
}