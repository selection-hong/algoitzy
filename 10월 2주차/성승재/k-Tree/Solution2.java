import java.util.*;
import java.io.*;

public class Solution2{
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
        System.out.println((totalDP()-underDP() + MOD) % MOD);
    }

    static int totalDP(){
        int window=0;

        for(int i=1;i<=n;i++){
            if(i-k-1<0){
                total[i] = window = (window + total[i-1]) % MOD;
                continue;
            }
            total[i] = window = ((window + total[i-1]) % MOD - total[i-k-1] + MOD) % MOD;
        }
        return total[n];
    }

    static int underDP(){
        int window = 0;

        for(int i=1;i<=n;i++){
            if(i-d<0){
                underD[i] = window = (window + underD[i-1]) % MOD;
                continue;
            }
            underD[i] = window = ((window + underD[i-1]) % MOD - underD[i-d] + MOD) % MOD;
        }
        return underD[n];
    }
}
