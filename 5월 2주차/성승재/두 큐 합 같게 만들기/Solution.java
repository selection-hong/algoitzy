class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int[] totalQueue = new int[queue1.length+queue2.length];
        System.arraycopy(queue1,0,totalQueue,0,queue1.length);
        System.arraycopy(queue2,0,totalQueue,queue1.length,queue2.length);
        int s=0,e=queue1.length;
        long totalSum = 0L,curSum=0L;
        for(int i=0;i<totalQueue.length;i++) {
            totalSum+=totalQueue[i];
            if(i<queue1.length) curSum+=totalQueue[i];
        }
        if(totalSum%2!=0)return -1;
        
        while(e<totalQueue.length){
            if(curSum>totalSum/2){
                curSum-=totalQueue[s++];
            }
            else if(curSum<totalSum/2){
                curSum+=totalQueue[e++];
            }
            else{
                break;
            }
        }
        if(e==totalQueue.length&&curSum!=totalSum/2)return -1;
        return s+e-queue1.length;
    }
}
