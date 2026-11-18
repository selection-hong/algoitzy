import java.util.*;
import java.io.*;

class Solution
{
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testCase = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for(int t = 1; t <= testCase; t++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int kacl = Integer.parseInt(st.nextToken());
            
            int arr[][] = new int [n][2];
            for(int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                arr[i][0] = Integer.parseInt(st.nextToken());
                arr[i][1] = Integer.parseInt(st.nextToken());
            }
            
            int score = solve(arr, n, kacl);
            bw.write("#" + t + " " + score + "\n");
        }
        bw.flush();
        bw.close();
	}
    
    private static int solve(int[][] arr, int n, int kacl) {
        int[] dp = new int[kacl + 1];
        int[] dp2 = new int[kacl + 1];
        
        for(int i = 0; i < n; i++) {
            int score = arr[i][0];
            int k = arr[i][1];
            for(int j = k; j <= kacl; j++) {
                dp2[j] =Math.max(dp[j - k] + score, dp[j]);
            }
            
            for(int j = 0; j < dp.length; j++) {
                dp[j] = dp2[j];
            }
        }
        return dp[kacl];
    }
}