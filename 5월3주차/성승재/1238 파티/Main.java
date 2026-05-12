import java.util.*;
import java.io.*;
public class Main{
	static final int INF=1000000000;
	static int N,M,X;
	static int[] to,weight,head,next;
	static int[][] dist;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		X=Integer.parseInt(st.nextToken());
		to=new int[M];
		weight=new int[M];
		next=new int[M];
		head=new int[N+1];		
		Arrays.fill(head, -1);
		dist=new int[N+1][N+1];
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int A=Integer.parseInt(st.nextToken());
			int B=Integer.parseInt(st.nextToken());
			int x=Integer.parseInt(st.nextToken());
			to[i]=B;
			weight[i]=x;
			next[i]=head[A];
			head[A]=i;
		}
		for(int i=1;i<=N;i++) {
			dijkstra(i);
		}
		
		int result = 0;
		for(int i=1;i<=N;i++) {
			result=Math.max(result, dist[i][X]+dist[X][i]);
		}
		System.out.println(result);
	}
	
	static void dijkstra(int start) {
		Arrays.fill(dist[start],INF);
		Queue<Long> queue = new PriorityQueue<>();
		queue.offer((long)start);
		dist[start][start]=0;
		while(!queue.isEmpty()) {
			long cur = queue.poll();
			int u=(int)cur;
			int w=(int)(cur>>>32);
			if(dist[start][u]<w)continue;
			for(int e=head[u];e!=-1;e=next[e]) {
				int nextV=to[e];
				if(dist[start][nextV]>dist[start][u]+weight[e]) {
					dist[start][nextV]=dist[start][u]+weight[e];
					queue.offer(((long)dist[start][nextV]<<32)|nextV);
				}
			}
		}
	}
}
