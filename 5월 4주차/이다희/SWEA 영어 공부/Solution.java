import java.io.*;
import java.util.*;

class Solution { // SWEA_10507_영어 공부  : 투 포인터
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++) {
		
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			
			int[] study = new int[n];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < n; i++) {
				study[i] = Integer.parseInt(st.nextToken());
			}
			
			int answer = 0;
			int start = 0;
			int end = 0;
			
			while (end < n) {
				
				// 빈칸 = 전체 기간 - 공부한 기간
				int blank = (study[end] - study[start] + 1) - (end - start + 1);
				
				if (blank <= p) { // 빈칸을 p로 다 채울 수 있는 경우
					answer = Math.max(answer, end - start + 1 + p);
					end++;
				} else { // 빈칸을 p로 다 채울 수 없는 경우
					start++;
				}
				
			}
			
			sb.append("#").append(test_case).append(" ").append(answer).append("\n");
			
		}
		
		System.out.println(sb);
		
	}
	
}