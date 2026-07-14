class Solution {
    
    final int INF = 1_000_000_001;
    
    public int solution(int[] arr) {
        int len = arr.length, top = -1;
        int[] stack = new int[len + 1];
        stack[++top] = INF;
        for(int i = len - 1; i >= 0; i--) {
            int val = arr[i];
            if(stack[top] > val) stack[++top] = val;
        }
        
        int answer = 0, left = INF;
        for(int val : arr) {
            if(stack[top] == val) {
                answer++;
                top--;
            } else if(val < left) {
                answer++;
                left = val;
            }   
        }
        
        return answer;
    }
}
