import java.util.*;
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1.length>nums2.length){
            return findMedianSortedArrays(nums2, nums1);
        }
        int m=nums1.length;
        int n=nums2.length;

        int l=0,r=m;
        while(l <= r){
            int i = (l + r) / 2;
            int j = (m + n + 1) / 2 - i;
            if (i > 0 && j < n && nums1[i-1] > nums2[j]) {
                r = i - 1;
            }
            else if (j > 0 && i < m && nums2[j-1] > nums1[i]) {
                l = i + 1;
            }
            else{
                int leftMax;
                
                if(i==0) leftMax = nums2[j-1];
                else if(j==0) leftMax = nums1[i-1];
                else leftMax = Math.max(nums1[i-1],nums2[j-1]);

                if((m+n)%2==1) return leftMax;

                int rightMin;
                if(i==m) rightMin = nums2[j];
                else if(j==n) rightMin = nums1[i];
                else rightMin = Math.min(nums1[i],nums2[j]);

                return (leftMax + rightMin) / 2.0;
            }
        }
        return 0;
    }
}
