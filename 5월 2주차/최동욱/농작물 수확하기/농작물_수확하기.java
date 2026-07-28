import java.util.*;
import java.io.*;

class Solution {
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testCase = Integer.parseInt(br.readLine());
        
        for(int t = 1; t <= testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            String[] arr = new String[n];
            for(int i = 0; i < n; i++) {
                arr[i] = br.readLine();
            }
            
            int left = n / 2, right = n / 2;
            int value = 0;
            for(int i = 0; i < n; i++) {
                for(int j = left; j <= right; j++) {
                    value += arr[i].charAt(j) - '0';
                }
                if(i < n /2) {
                    left--;
                    right++;
                }
                else {
                    left++;
                    right--;
                }
            }
            bw.write("#" + t + " " + value + "\n");
        }
        bw.flush();
        bw.close();
	}
}