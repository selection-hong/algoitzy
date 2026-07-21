class Solution {
    //연결되어 있는 네트워크는 1개의 그룹으로 묶임
    boolean [] v;
    public int solution(int n, int[][] c) {
        v=new boolean[n];
        int cnt=0;
        
        for(int i=0; i<n; i++){
            if(!v[i]){
                dfs(i, n, c);
                cnt++;
            }
        }
        return cnt;
    }
    public void dfs(int i, int n, int[][] c){
        v[i]=true;
        
        for(int j=0; j<n; j++){
            if(c[i][j]==1&&!v[j]){
                dfs(j, n, c);
            }   
        }
        
    }
}