package SWEA_D2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SWEA2805_농작물수확하기 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			int[][] board = new int[N][N];
			for (int i = 0; i < N; i++) {
				String line = br.readLine();
				for (int j = 0; j < N; j++) {
					board[i][j] = line.charAt(j) - '0';
				}
			}
			int idx = N / 2;
			int sum=0;
			for (int i = 0; i < N; i++) {
				if (i <= idx) {
					for (int j = idx - i; j <= idx + i; j++) {
						sum+=board[i][j];
					}
				} else {
					int k=(N-1)-i;
					for (int j = idx - k; j <= idx + k; j++) {
						sum+=board[i][j];
						
					}
				}
			}
			System.out.println("#"+tc+" "+sum);
		}

	}

}
