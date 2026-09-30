package SWEA_D2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SWEA26504_MST만들기{
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {

			
			int n = Integer.parseInt(br.readLine());
			//완전 그래프 간선 개수
			int m = n * (n-1) / 2;
			int[] c = new int[m];
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<m; i++) {
				c[i] = Integer.parseInt(st.nextToken());
			}
			
			//최소
			Arrays.sort(c);
			//System.out.println(Arrays.toString(c));
			long min_sum = 0;
			for(int i=0; i<n-1; i++) min_sum += c[i];
			
			
			//최대
			long max_sum = 0;
			int idx = 0;
			for(int i=0; i<n-1; i++) {
				max_sum += c[idx];
				idx+=(i+1);
			}
			
			sb.append(min_sum).append(" ").append(max_sum).append("\n");
			
		}
		System.out.println(sb.toString());
	}
}
