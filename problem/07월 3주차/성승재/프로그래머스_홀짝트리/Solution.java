import java.util.*;

class Solution {
    Node[] node = new Node[1_000_001];
    boolean[] visited = new boolean[1_000_001];
    int[] answer = new int[2];
    public int[] solution(int[] nodes, int[][] edges) {
        for(int i=0;i<nodes.length;i++){
            node[nodes[i]] = new Node(nodes[i]);
        }
        for(int i=0;i<edges.length;i++){
            int node1 = edges[i][0];
            int node2 = edges[i][1];
            node[node1].adjNodes.add(node[node2]);
            node[node2].adjNodes.add(node[node1]);
        }
        
        for(int i=0;i<1_000_001;i++){
            if(node[i] == null || visited[i] == true) continue;
            check(i);
        }
        return this.answer;
    }
    
    void check(int index){
        int reverse=0,notReverse=0;
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(node[index]);
        visited[index]=true;
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            if(cur.index%2 == cur.adjNodes.size()%2) notReverse++;
            else reverse++;
            
            for(Node n:cur.adjNodes){
                if(visited[n.index])continue;
                queue.offer(n);
                visited[n.index]=true;
            }
        }
        if(reverse==1) answer[1]++;
        if(notReverse==1) answer[0]++;
        return;
    }
    
    class Node{
        int index;
        List<Node> adjNodes = new ArrayList<>();
        public Node(int index){
            this.index = index;
        }
    }
}
