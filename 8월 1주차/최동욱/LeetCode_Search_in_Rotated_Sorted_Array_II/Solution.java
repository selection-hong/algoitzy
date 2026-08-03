class Solution {
    public boolean search(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid;

        while(left <= right) {
            mid = (left + right) >> 1;

            if(nums[mid] == target) return true;

            if(nums[left] == nums[mid] && nums[right] == nums[mid]) {
                left++;
                right--;
                continue;
            }

            if(nums[left] <= nums[mid]) {
                if(nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if(nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return false;
    }
}