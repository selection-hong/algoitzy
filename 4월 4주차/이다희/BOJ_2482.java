import java.io.*;
import java.util.*;

public class Main {
	
	static final int div = 1_000_000_003;
	
    public static void main(String[] args) throws Exception {
        
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());
        int[][] dp = new int[N+1][K+1];
        
        for (int i = 1; i <= N; i++) {
        	dp[i][0] = 1; // 0개 선택하는 경우의 수
        	dp[i][1] = i; // 1개 선택하는 경우의 수
        }
        
        for (int i = 2; i <= N; i++) {
        	for (int j = 2; j <= K && j <= N; j++) {
        		// [1] i-1번, i+1번 선택 불가
        		// [2] i번 선택 불가
        		dp[i][j] = (dp[i-2][j-1] + dp[i-1][j]) % div;
        	}
        }
        
        if (N / 2 < K) {
        	System.out.println(0);
        } else if (K == 1) {
        	System.out.println(N);
        } else {
        	// [1] N번, 1번, 2번 선택 불가
    		// [2] N번 선택 불가
            System.out.println((dp[N-3][K-1] + dp[N-1][K]) % div);
        }
        
    }

}
