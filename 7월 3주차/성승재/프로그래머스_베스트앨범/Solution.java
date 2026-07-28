import java.util.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        
        Map<String, int[]> map = new HashMap<>();
        List<int[]> list = new ArrayList<>();
        for(int i=0;i<genres.length;i++){
            if(map.get(genres[i])==null){
                int[] a = new int[3];
                map.put(genres[i], a);
                list.add(a);
            }
            int[] arr = map.get(genres[i]);
            arr[0] += plays[i];
            if(arr[1]/10000<plays[i]){
                arr[2]=arr[1];
                arr[1]=plays[i]*10000+i;
            }
            else if(arr[2]/10000<plays[i]){
                arr[2]=plays[i]*10000+i;
            }
        }
        list.sort((o1,o2)-> o2[0]-o1[0]);
        List<Integer> answer = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            int[] arr = list.get(i);
            answer.add(arr[1]%10000);
            if(arr[2]!=0) answer.add(arr[2]%10000);
        }
        
        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
}
