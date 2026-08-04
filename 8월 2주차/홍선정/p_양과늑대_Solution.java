package 8월 1주차;

import java.util.*;
class 양과늑대_Solution {
    //백트래킹 + dfs
    
    class Nd {
        int v, w;
        Nd(int v, int w) { this.v = v; this.w = w;
        }
    }
    public int solution(int[] info, int[][] edges) {
        List<List<Nd>> g = new ArrayList<>();
        for (int i = 0; i < info.length; i++) g.add(new ArrayList<>());
        for (int[] e : edges) g.get(e[0]).add(new Nd(e[1], 0));
        int n = info.length;

