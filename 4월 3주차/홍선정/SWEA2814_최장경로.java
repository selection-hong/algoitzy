package SWEA_D2;

	import java.io.BufferedReader;
	import java.io.InputStreamReader;
	import java.util.ArrayList;
	import java.util.StringTokenizer;

	public class SWEA2814_최장경로 {
	    static int N, M;
	    static ArrayList<Integer>[] adjList;
	    static boolean[] visited;
	    static int maxLength;

	    public static void main(String[] args) throws Exception {
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        StringBuilder sb = new StringBuilder();
	        
	        int T = Integer.parseInt(br.readLine().trim());
	        
	        for (int t = 1; t <= T; t++) {
	            StringTokenizer st = new StringTokenizer(br.readLine().trim());
	            N = Integer.parseInt(st.nextToken());
	            M = Integer.parseInt(st.nextToken());
	            
	            adjList = new ArrayList[N + 1];
	            for (int i = 1; i <= N; i++) {
	                adjList[i] = new ArrayList<>();
	            }
	            maxLength = 0;
	            
	            for (int i = 0; i < M; i++) {
	                st = new StringTokenizer(br.readLine().trim());
	                int u = Integer.parseInt(st.nextToken());
	                int v = Integer.parseInt(st.nextToken());
	                
	                adjList[u].add(v);
	                adjList[v].add(u);
	            }
	            
	            visited = new boolean[N + 1];
	            for (int i = 1; i <= N; i++) {
	                visited[i] = true;
	                dfs(i, 1);
	                visited[i] = false; 
	            }
	            
	            sb.append("#").append(t).append(" ").append(maxLength).append("\n");
	        }
	        System.out.print(sb.toString());
	    }

	    static void dfs(int current, int length) {
	        if (length > maxLength) {
	            maxLength = length;
	        }

	        for (int next : adjList[current]) {
	            if (!visited[next]) {
	                visited[next] = true;     
	                dfs(next, length + 1);   
	                visited[next] = false;
	            }
	        }
	    }
	}
