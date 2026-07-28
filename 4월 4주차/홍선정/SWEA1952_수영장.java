package SWEA_D2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1952_수영장 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine().trim());

		for (int t = 1; t <= T; t++) {
			int[] prices = new int[4];
			StringTokenizer st = new StringTokenizer(br.readLine().trim());
			for (int i = 0; i < 4; i++) {
				prices[i] = Integer.parseInt(st.nextToken());
			}

			int[] days = new int[13];
			st = new StringTokenizer(br.readLine().trim());
			for (int i = 1; i <= 12; i++) {
				days[i] = Integer.parseInt(st.nextToken());
			}

			int[] dp = new int[13];

			for (int i = 1; i <= 12; i++) {
				dp[i] = dp[i - 1] + (days[i] * prices[0]);

				dp[i] = Math.min(dp[i], dp[i - 1] + prices[1]);

				if (i >= 3) {
					dp[i] = Math.min(dp[i], dp[i - 3] + prices[2]);
				}
			}

			int minCost = Math.min(dp[12], prices[3]);

			sb.append("#").append(t).append(" ").append(minCost).append("\n");
		}
		System.out.print(sb.toString());
	}
}
