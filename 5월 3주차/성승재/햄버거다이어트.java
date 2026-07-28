import java.util.*;
import java.io.*;
public class Solution {
	static int N,L;
	static int[][] ingr,d;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int t=Integer.parseInt(br.readLine());
		for(int test_case=1;test_case<=t;test_case++) {
			st = new StringTokenizer(br.readLine());
			N=Integer.parseInt(st.nextToken());
			L=Integer.parseInt(st.nextToken());
			ingr=new int[N][2];//0:맛,1:칼로리
			d=new int[N+1][L+1];//0:몇번째 재료인지, 1:남은 칼로리
			Arrays.fill(d[0], 0);
			for(int i=1;i<=N;i++) {
				Arrays.fill(d[i], -1);
				d[i][0]=0;
			}
			for(int i=0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				ingr[i][0]=Integer.parseInt(st.nextToken());
				ingr[i][1]=Integer.parseInt(st.nextToken());
			}
			System.out.println("#"+test_case+" "+dp(1,L));
		}
	}
	
	static int dp(int index,int leftCal) {
		int currentScore=ingr[index-1][0],currentCal=ingr[index-1][1];
		if(index==N) {
			if(currentCal>leftCal) {
				return 0;
			}
			return currentScore;
		}
		if(d[index][leftCal]!=-1) {
			return d[index][leftCal];
		}
		if(currentCal>leftCal) {
			d[index][leftCal]=dp(index+1,leftCal);
			return d[index][leftCal];
		}
		d[index][leftCal]=Math.max(currentScore+dp(index+1,leftCal-currentCal), dp(index+1,leftCal));
		return d[index][leftCal];
	}
}
