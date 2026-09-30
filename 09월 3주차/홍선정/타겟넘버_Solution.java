package 9월 3주차.홍선정;

class 타겟넘버_Solution {
    //완탐
    int ans =0;
    public int solution(int[] numbers, int target) {
        
        dfs(numbers, target, 0, 0);
        return ans;
    }
    
    public void dfs(int[] numbers, int target, int depth, int sum){
        if(depth==numbers.length){
            if(sum==target){
                ans++;
            }
            return;
        }
        dfs(numbers, target, depth+1, sum+numbers[depth]);
        dfs(numbers, target, depth+1, sum-numbers[depth]);
    }
}