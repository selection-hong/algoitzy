import java.util.*;
import java.io.*;

public class Solution {
    static int n;
    static Map<String, Integer> map = new HashMap<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        n=Integer.parseInt(br.readLine());
        
        for(int i=0;i<n;i++){
            String name = br.readLine();
            if(!map.containsKey(name)){
                map.put(name,1);
                sb.append("OK\n");
                continue;
            }
            
            int num = map.get(name);
            map.put(name,num+1);
            sb.append(name).append(num).append("\n");
        }

        System.out.print(sb);
    }
}
