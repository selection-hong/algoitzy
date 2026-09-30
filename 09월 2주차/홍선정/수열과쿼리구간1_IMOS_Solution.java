package 9월 2주차.홍선정;

public class 수열과쿼리구간1_IMOS_Solution {
    //차분배열 활용 
    public int[] solution(int[] arr, int[][] queries) {
        int n=arr.length;
        //dif 배열 : 앞 인덱스와 차이점을 기록.
        // s를 1로 기록, e 다음 인덱스를 -1 로 기록. 
        int [] dif=new int[n+1];
        
        for(int []query : queries){
            
            dif[query[0]]++;
            dif[query[1]+1]--;
        }
        
        int sumV=0;
        for(int i=0; i<n; i++){
            sumV+=dif[i];
            arr[i]+=sumV;
        }
        
        return arr;
    }

    
}
