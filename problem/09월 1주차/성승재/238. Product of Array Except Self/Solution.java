class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] answer = new int[nums.length];
        int total = 1;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==0){
                for(int j=i+1;j<nums.length;j++){
                    if(nums[j]==0) return answer;
                    total*=nums[j];
                }
                answer[i] = total;
                return answer;
            }
            total*=nums[i];
        }

        for(int i=0;i<nums.length;i++) answer[i] = total/nums[i];
        return answer;
    }
}
