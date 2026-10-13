import java.util.*;

class Solution {

    static List<Integer>[] list;
    static int res;

    public int longestPath(int[] parent, String s) {
        int n = parent.length;
        list = new ArrayList[n];
        res = 0;
        for(int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
        }

        for(int i = 1; i < n; i++) {
            list[parent[i]].add(i);
        }

        dfs(0, s);
        return res;
    }

    private int dfs(int idx, String s) {
        char p = s.charAt(idx);

        int val1 = 0, val2 = 0;
        for(int i : list[idx]) {
            int temp = dfs(i, s);
            if(p != s.charAt(i)) {
                if(val1 < temp) {
                    val2 = val1;
                    val1 = temp;
                } else if(val2 < temp) {
                    val2 = temp;
                }
            }
        }

        if(val1 + val2 + 1 > res) res = val1 + val2 + 1;
        return val1 + 1;
    }
}