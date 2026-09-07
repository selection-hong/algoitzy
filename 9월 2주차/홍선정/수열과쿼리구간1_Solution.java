package 9월 2주차.홍선정;

class 수열과쿼리구간1_Solution {
    public int[] solution(int[] arr, int[][] queries) {
        for(int [] query : queries){
            int s=query[0];
            int e=query[1];
            
            for(int i=s; i<=e; i++){
                arr[i]++;
            }   
        }
        
        return arr;
    }
}