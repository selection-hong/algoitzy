import java.util.*;
import java.io.*;

public class Solution{
    static final int MOD = 1_000_000_007;
    static int n,k,d;
    static int[] total, underD;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n=Integer.parseInt(st.nextToken());
        k=Integer.parseInt(st.nextToken());
        d=Integer.parseInt(st.nextToken());
        total = new int[n+1];
        underD = new int[n+1];
        total[0]=1; underD[0]=1;
        System.out.println((totalDP(n)-underDP(n) + MOD) % MOD);
    }

    static int totalDP(int cur){
        if(cur < 0) return 0;
        if(total[cur] != 0) return total[cur];
        for(int i=1;i<=k;i++){
            total[cur] = (total[cur] + (totalDP(cur-i) % MOD)) % MOD;
        }
        return total[cur];
    }

    static int underDP(int cur){
        if(cur < 0) return 0;
        if(underD[cur] != 0) return underD[cur];
        for(int i=1;i<d;i++){
            underD[cur] = (underD[cur] + (underDP(cur-i) % MOD)) % MOD;
        }
        return underD[cur];
    }
}
