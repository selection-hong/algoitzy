import java.util.*;
import java.io.*;

class Solution
{

	public static void main(String args[]) throws Exception
	{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;   
        StringBuilder sb = new StringBuilder();
		for(int test_case = 1; test_case <= T; test_case++)
		{
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
            int[] lands = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i=0;i<N;i++) lands[i] = Integer.parseInt(st.nextToken());

            int s=0,e=0,sum=0,portfolio=0;
            while(e < N || sum > M){
                if(sum <= M){
                    sum += lands[e++];
                    if(sum==M) portfolio++;
                }
                else{
                    sum -= lands[s++];
                    if(sum==M) portfolio++;
                }
            }

            sb.append("#").append(test_case).append(" ").append(portfolio).append("\n");
		}
        System.out.println(sb);
	}
}
