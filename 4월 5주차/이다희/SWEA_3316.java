import java.io.*;
import java.util.*;

public class SWEA_3316 { // 동아리실 관리하기 : Bitmasking + DP
	
	public static void main(String[] args) throws Exception {
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			
			String str = br.readLine();
			int len = str.length();
			int[][] dp = new int[len][16];
			
			int key = 1; // 열쇠 (첫째날 : A)
			int manager; // 책임자
			
			// 첫째날
			int i = 0;
			for (int j = 1; j < 16; j++) {
				manager = 1 << (str.charAt(i) - 'A');
				if ((j & key) != 0 && (j & manager) != 0) { // 열쇠 & 책임자 체크
					dp[i][j] = 1;
				}
			}
			
			// 둘째날 ~ 마지막날
			for (i = 1; i < len; i++) { // Day
				manager = 1 << (str.charAt(i) - 'A');
				for (int j = 1; j < 16; j++) { // Today
					// [1] 책임자 체크
					if ((j & manager) == 0) continue;
					// [2] 열쇠 체크
					for (int k = 1; k < 16; k++) { // Yesterday
						// [2-1] 어제 경우의 수가 0인 경우 --> 불가능
						// [2-2] 어제 있던 사람이 오늘 한 명도 없는 경우 --> 불가능
						if (dp[i-1][k] == 0 || (j & k) == 0) continue;
						// [2-3] 어제의 경우의 수 합산
						dp[i][j] = (dp[i][j] + dp[i-1][k]) % 1_000_000_007;
					}
				}
			}
			
			// 경우의 수 합산
			int ans = 0;
			for (int j = 1; j < 16; j++) {
				ans =  (ans + dp[len-1][j]) % 1_000_000_007;
			}
			sb.append("#").append(tc).append(" ").append(ans).append("\n");
			
		}
		
		System.out.println(sb);

	}

}
