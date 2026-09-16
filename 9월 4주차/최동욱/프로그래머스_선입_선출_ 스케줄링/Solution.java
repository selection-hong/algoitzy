import java.util.*;

class Solution {
    public int solution(int n, int[] cores) {                
        long time = binarySearch(n, cores);
        
        long cnt = cores.length;
        for(int core : cores) {
            cnt += (time - 1) / core;
        }
        
        for(int i = 0; i < cores.length; i++) {
            if(time % cores[i] == 0) {
                cnt++;
                
                if(cnt == n) return i + 1;
            }
        }
        
        return -1;
    }
    
    private static long binarySearch(int n, int[] cores) {
        int temp = 10_001;
        for(int core : cores) {
            if(temp > core) temp = core;
        }
        
        long left = 0;
        long right = temp * n;
        
        int len = cores.length;
        
        while(left < right) {
            long mid = (right + left) >> 1;
            
            long cnt = len;
            for(int core : cores) {
                cnt += mid / core;
            }
            
            if(cnt >= n)  {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
}