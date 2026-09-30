import java.io.*;
import java.util.*;

public class SWEA_13736 { // 사탕 분배 : 분할 정복

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {

			st = new StringTokenizer(br.readLine());
			long A = Long.parseLong(st.nextToken());
			long B = Long.parseLong(st.nextToken());
			long sum = A + B;
			long cnt = Long.parseLong(st.nextToken());
 
			// A * 2
			// = (A * 2) % sum
			// A는  B보다 작기 때문에 A*2는 sum보다 작으므로 (A*2)%sum은 A*2와 같다.
			
			// B - A
			// = (B * 2) - sum
			// = (B * 2) % sum
			// B는  A보다 크기 때문에 B*2는 sum보다 크고 sum*2보다 작으므로 (B*2)%sum은 B-A와 같다.
			
			// (A * 2^cnt) % sum
			long ans = (A * power(cnt, sum)) % sum;
			if (ans > (sum / 2)) ans = sum - ans;
			
			sb.append("#").append(tc).append(" ").append(ans).append("\n");
			
		}

		System.out.println(sb);
		
	}

	private static long power(long cnt, long mod) {
		long res = 1;
		long num = 2;
		
		while (cnt > 0) {
			if (cnt % 2 == 1) {
				res = (res * num) % mod;
			}
			cnt /= 2; // 분할
			num = (num * num) % mod; // 정복
		}
		
		return res;
	}
	
	

}
